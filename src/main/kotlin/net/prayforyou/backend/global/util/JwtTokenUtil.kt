package net.prayforyou.backend.global.util

import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenUtil {

    companion object {
        private const val PRAY_FOR_YOU_KEY = "pray4you"
    }

    fun create(
        userId: Long,
        now: LocalDateTime = LocalDateTime.now()
    ): String =
        Jwts.builder()
            .setClaims(Jwts.claims().setSubject(userId.toString()))
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS256, PRAY_FOR_YOU_KEY)!!
            .setExpiration(Date.from(now.plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant()))
            .compact()!!

    fun getTokenFromHeader(request: HttpServletRequest): String =
        request.getHeader("Authorization")

    fun getClaims(signingKey: String, token: String): Jws<Claims> =
        Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token)

    fun getExpiredAt(token: String): LocalDateTime? {
        val claims = getClaims(PRAY_FOR_YOU_KEY, token)

        val date = claims.body.expiration
        return date.toInstant().atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun validToken(token: String): Boolean {
        if (token.isEmpty()) {
            return false
        }

        return try {
            if (!token.startsWith("Bearer")) {
                return false
            }
            val replaceToken = token.replace("Bearer ", "")
            getExpiredAt(replaceToken)!! > LocalDateTime.now()
        } catch (e: Exception) {
            false
        }
    }
}