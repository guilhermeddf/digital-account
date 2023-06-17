package com.dock.bank.digitalaccount.ports.spi

import com.dock.bank.digitalaccount.core.domain.Login

interface AuthenticationServicePort {
    fun authenticate(login: Login): Login
}