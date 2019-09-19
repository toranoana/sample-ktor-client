package com.example

import extention.exec
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun executeRawSql() {

    // DB接続情報を設定(MySQL)
    val sampleDb = Database.connect(
        "jdbc:mysql://localhost:3306/sample_db",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "sample_user" ,
        password = "sample_pass")

    // SQL
    val sql = """SELECT SALES_DATE
        , SALES_AMOUNT
        , SALES_VOLUME
        FROM SAMPLE_SALES
    """.trimMargin()

    // トランザクション取得
    transaction(sampleDb) {
        // SQL実行
        val resultSet = this.exec(sql, body = {}) ?: return@transaction
        // 結果の取得
        while (resultSet.next()) {
            val salesDate = resultSet.getString("SALES_DATE")
            val salesAmount = resultSet.getString("SALES_AMOUNT")
            val salesVolume = resultSet.getString("SALES_VOLUME")
            println("売上日付：$salesDate 売上金額：$salesAmount 円 売上数量：$salesVolume 点")
        }
    }
}