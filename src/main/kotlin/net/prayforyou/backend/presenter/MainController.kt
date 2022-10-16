package net.prayforyou.backend.presenter

import net.prayforyou.backend.global.common.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/")
    fun main(): CommonResponse<Boolean> =
        CommonResponse.convert(true)
}