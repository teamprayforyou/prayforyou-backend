package net.prayforyou.backend.infra.crawler.webclient

import net.prayforyou.backend.infra.crawler.webclient.RandomProxy.changeIp
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

@Service
class ApiService<T>(
    private val restTemplate: RestTemplate
) {
    val logger: Logger = Logger.getLogger(ApiService::class.java.name)

    fun post(url: String, httpHeaders: MultiValueMap<String, String>, body: Any, clazz: Class<T>): ResponseEntity<T> {
        if (changeIp) {
            val ip = RandomProxy.getRandomProxy().ip
            logger.log(LogRecord(Level.INFO, "ip 변경 ${ip}"))
            val factory = SimpleClientHttpRequestFactory()
            val address = InetSocketAddress(ip, RandomProxy.getRandomProxy().port)
            val proxy = Proxy(Proxy.Type.HTTP, address)
            factory.setProxy(proxy)
            restTemplate.requestFactory = factory
            changeIp = false
        }
        return callApiEndPoint(url, HttpMethod.POST, httpHeaders, body, clazz)
    }

    fun get(url: String, httpHeaders: MultiValueMap<String, String>, clazz: Class<T>): ResponseEntity<T> {
        return callApiEndPoint(url, HttpMethod.GET, httpHeaders, null, clazz)
    }

    private fun callApiEndPoint(
        url: String,
        httpMethod: HttpMethod,
        httpHeaders: MultiValueMap<String, String>,
        body: Any?,
        clazz: Class<T>
    ): ResponseEntity<T> {
        return restTemplate.exchange(url, httpMethod, HttpEntity(body, httpHeaders), clazz)
    }
}
