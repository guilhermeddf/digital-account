package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Login
import com.dock.bank.digitalaccount.ports.api.LoginServicePort
import com.dock.bank.digitalaccount.ports.spi.AuthenticationServicePort

class LoginServicePortImpl(
    private val authenticationService: AuthenticationServicePort
): LoginServicePort {

    override fun login(login: Login) = authenticationService.authenticate(login)

}