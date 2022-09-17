package net.prayforyou.backend.infrastructure.crawler.webclient

import org.springframework.util.LinkedMultiValueMap

private const val ACCPET = "application/json, text/plain, */*"

private const val CONTENT_TYPE = "application/json;charset=UTF-8"

private val USER_AGENT = listOf(
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36",
    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36",
    "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0"
)

private const val ACCEPT_ENCODING = "gzip, deflate, br"

private const val ORIGIN = "https://barracks.sa.nexon.com"

fun setMatchHeaders(): LinkedMultiValueMap<String, String> {
    val multiValueMap = LinkedMultiValueMap<String, String>()
    multiValueMap["Accept"] = ACCPET
    multiValueMap["Content-Type"] = CONTENT_TYPE
    multiValueMap["User-Agent"] = USER_AGENT.random()
    multiValueMap["Referer"] = "https://barracks.sa.nexon.com/2047101791/match"
    multiValueMap["Origin"] = ORIGIN
    multiValueMap["Accept-Encoding"] = ACCEPT_ENCODING
    multiValueMap["Content-Length"] = "90"
    return multiValueMap
}

fun setBattleLogHeaders(): LinkedMultiValueMap<String, String> {
    val multiValueMap = LinkedMultiValueMap<String, String>()
    multiValueMap["Accept"] = ACCPET
    multiValueMap["Content-Type"] = CONTENT_TYPE
    multiValueMap["User-Agent"] = USER_AGENT.random()
    multiValueMap["Origin"] = ORIGIN
    multiValueMap["Accept-Encoding"] = ACCEPT_ENCODING
    multiValueMap["Content-Length"] = "0"
    return multiValueMap
}

fun setUserHeaders(): LinkedMultiValueMap<String, String> {
    val multiValueMap = LinkedMultiValueMap<String, String>()
    multiValueMap["Accept"] = ACCPET
    multiValueMap["Content-Type"] = CONTENT_TYPE
    multiValueMap["User-Agent"] = USER_AGENT.random()
    multiValueMap["Origin"] = ORIGIN
    multiValueMap["Accept-Encoding"] = ACCEPT_ENCODING
    multiValueMap["Content-Length"] = "0"
    return multiValueMap
}

fun setDummyHeaders(): LinkedMultiValueMap<String, String> {
    val multiValueMap = LinkedMultiValueMap<String, String>()
    multiValueMap["Accept"] = ACCPET
    multiValueMap["Content-Type"] = CONTENT_TYPE
    multiValueMap["User-Agent"] = USER_AGENT.random()
    multiValueMap["Origin"] = ORIGIN
    multiValueMap["Accept-Encoding"] = ACCEPT_ENCODING
    multiValueMap["Content-Length"] = "0"
    return multiValueMap
}
