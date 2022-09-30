package net.prayforyou.backend.global.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar


class DateUtil {
    companion object {
        fun getMondayDate(): LocalDate {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            return LocalDate.ofInstant(cal.toInstant(), ZoneId.systemDefault())
        }
    }
}
