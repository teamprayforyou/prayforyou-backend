package net.prayforyou.backend.global.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat
import java.util.Date
import javax.annotation.PostConstruct

@Configuration
class QueryPrettyConfig {
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.name
    }
}

/**
 * jpa 로그를 정렬된 형태로 출력하도록 하는 책임을 가지는 클래스
 */
class P6spyPrettySqlFormatter : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String,
        elapsed: Long,
        category: String,
        prepared: String,
        rawSql: String,
        url: String
    ): String {
        var sql: String? = rawSql
        sql = formatSql(category, sql)
        val currentDate = Date()
        val format = SimpleDateFormat("yy.MM.dd HH:mm:ss")

        return format.format(currentDate) + " | " + "OperationTime : " + elapsed + "ms" + sql
    }

    private fun formatSql(category: String, rawSql: String?): String? {
        var sql = rawSql
        if (sql == null || sql.trim { it <= ' ' } == "") return sql

        if (Category.STATEMENT.name == category) {
            val tmpsql = sql.trim { it <= ' ' }.lowercase()
            sql = if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                FormatStyle.DDL.formatter.format(sql)
            } else {
                FormatStyle.BASIC.formatter.format(sql)
            }
            sql = "|\nSQL:$sql"
        }
        return sql
    }
}
