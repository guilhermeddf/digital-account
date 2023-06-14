package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.usecase.TokenServiceImpl
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.rest.dto.CreateLoginRequest
import com.dock.bank.digitalaccount.utils.CustomUserDetails
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
class LoginController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenServiceImpl
) {
    companion object {
        private val logger = LoggerFactory.getLogger(LoginController::class.java)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: CreateLoginRequest): String {
        val requestId = UUID.randomUUID()
        MDC.put("request_id", requestId.toString())
        MDC.put("username", loginRequest.username)
        MDC.put("password", loginRequest.password)

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        val authenticate  = manager.authenticate(usernamePasswordAuthenticationToken)
        val user = authenticate.principal as CustomUserDetails
        val userTable = user.toTable()

        logger.info("User ${userTable.username} was logged with success with roles: ${userTable.role}")
        MDC.clear()
        return tokenService.tokenGenerator(userTable)
    }
}
