package com.dock.bank.digitalaccount.infra.rest.controllers

import com.dock.bank.digitalaccount.core.domain.Login
import com.dock.bank.digitalaccount.core.usecase.TokenServiceImpl
import com.dock.bank.digitalaccount.infra.rest.converter.toTable
import com.dock.bank.digitalaccount.utils.CustomUserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LoginController(
    private val manager: AuthenticationManager,
    private val tokenService: TokenServiceImpl
) {
    @PostMapping("/login")
    fun login(@RequestBody login: Login): String {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(login.username, login.password)
        val authenticate  = manager.authenticate(usernamePasswordAuthenticationToken)
        val user = authenticate.principal as CustomUserDetails
        val userTable = user.toTable()
        return tokenService.tokenGenerator(userTable)
    }
}