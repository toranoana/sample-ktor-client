package extention

import org.jetbrains.exposed.sql.Transaction
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * 任意のSQLを実行可能なTransaction拡張.
 *
 * @param sql SQL
 * @param body PreparedStatementで指定したパラメタ
 *
 * @return SQL実行結果をResultSetで返す
 */
fun Transaction.exec(sql: String, body: PreparedStatement.() -> Unit) : ResultSet? {
    return connection.prepareStatement(sql).apply(body).run {
        if (sql.toLowerCase().startsWith("select ")) {
            executeQuery()
        } else {
            executeUpdate()
            resultSet
        }
    }
}