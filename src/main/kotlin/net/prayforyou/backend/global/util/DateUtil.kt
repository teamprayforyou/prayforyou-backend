package net.prayforyou.backend.global.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


class DateUtil {
    companion object {
        fun getMondayDate(): LocalDate {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            return LocalDate.ofInstant(cal.toInstant(), ZoneId.systemDefault())
        }

        private object TIME_MAXIMUM {
            const val SEC = 60
            const val MIN = 60
            const val HOUR = 24
            const val DAY = 30
            const val MONTH = 12
        }

        fun toDate(date: String): Date {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return formatter.parse(date)
        }

        fun calculateTime(date: Date): String? {
            val curTime = System.currentTimeMillis()
            val regTime: Long = date.time
            var diffTime = (curTime - regTime) / 1000
            var msg: String? = null
            if (diffTime < TIME_MAXIMUM.SEC) {
                // sec
                msg = diffTime.toString() + "초 전"
            } else if (TIME_MAXIMUM.SEC.let { diffTime /= it; diffTime } < TIME_MAXIMUM.MIN) {
                // min
                msg = diffTime.toString() + "분 전"
            } else if (TIME_MAXIMUM.MIN.let { diffTime /= it; diffTime } < TIME_MAXIMUM.HOUR) {
                // hour
                msg = diffTime.toString() + "시간 전"
            } else if (TIME_MAXIMUM.HOUR.let { diffTime /= it; diffTime } < TIME_MAXIMUM.DAY) {
                // day
                msg = diffTime.toString() + "일 전"
            } else if (TIME_MAXIMUM.DAY.let { diffTime /= it; diffTime } < TIME_MAXIMUM.MONTH) {
                // day
                msg = diffTime.toString() + "달 전"
            } else {
                msg = diffTime.toString() + "년 전"
            }
            return msg
        }

        fun getRemainDay(endSeason: LocalDate?): Long {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")

            val endDate = dateFormat.parse(endSeason.toString()).time
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time.time

            return (endDate - today) / (24 * 60 * 60 * 1000)
        }
    }
}
