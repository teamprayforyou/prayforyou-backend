package net.prayforyou.backend.global.util

import kotlin.math.round

class MathUtil {
    companion object {
        fun getRate(kill: Int, death: Int): Double {
            val result = (kill * 100).toDouble() / (kill + death).toDouble()
            return round((result * 100) / 100)
        }

    }
}
