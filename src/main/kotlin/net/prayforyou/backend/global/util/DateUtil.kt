package net.prayforyou.backend.global.util

import java.time.LocalDate

class DateUtil {
    companion object {
        fun getMondayDate(now: LocalDate): LocalDate {
            val day = now.dayOfWeek.value
            val diff = now.dayOfMonth - day + if (day == 0) -6 else 1
            return LocalDate.of(now.year, now.month, diff)!!
        }
    }
}