/*package com.dock.bank.digitalaccount.api.v1.controller

import com.dock.bank.digitalaccount.api.v1.mapper.toEntity
import com.dock.bank.digitalaccount.api.v1.mapper.toResponse
import com.dock.bank.digitalaccount.api.v1.request.CreateLoginRequest
import com.dock.bank.digitalaccount.api.v1.response.CreateLoginResponse
import com.dock.bank.digitalaccount.ports.api.LoginServicePort
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class LoginController(
    private val loginServicePort: LoginServicePort
) {
    companion object {
        private val logger = LoggerFactory.getLogger(LoginController::class.java)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: CreateLoginRequest): CreateLoginResponse {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("username", loginRequest.username)
        MDC.put("password", loginRequest.password)


        val response = loginServicePort.login(loginRequest.toEntity())
        MDC.clear()

        return response.toResponse()
    }
}

 */
