package slack

/**
 * Slackに送信するメッセージのデータクラス.
 *
 * @param channel Slackチャンネル
 * @param username 発言ユーザ名
 * @param text 発言内容
 * @param icon_emoji アイコンに絵文字を設定(カスタム絵文字も設定可能)
 */
data class SlackMessage (
    val channel: String,
    val username: String,
    val text: String,
    val icon_emoji: String)