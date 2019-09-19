package com.example

import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import io.ktor.client.features.logging.*
import io.ktor.client.response.HttpResponse
import slack.SlackMessage
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    // ApacheHttpClientのインスタンスを生成
    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            level = LogLevel.HEADERS
        }
    }
    // Incoming Webhooksの設定
    val slackHost = "hooks.slack.com"
    val slackPath = "IncomingWebhooksのURLの「/services」以降を指定します"
    val slackChannel = "チャンネルを指定できます"

    val message = "ここにメッセージを入れます"

    // Httpリクエストの送信はコルーチン内で行います
    runBlocking {
        // 送信するメッセージを作成します。
        val body = SlackMessage(slackChannel, "ユーザ名を指定できます", message, ":wink:")
        try {
            // POSTリクエストを送信
            client.post<HttpResponse> (scheme = "https",
                host = slackHost,
                path = slackPath,
                // 送信メッセージをJSON形式に変換
                body = Gson().toJson(body))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    client.close()
    exitProcess(0)
}