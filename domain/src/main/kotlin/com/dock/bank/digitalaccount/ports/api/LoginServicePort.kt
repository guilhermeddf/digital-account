package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.Login


interface LoginServicePort {

    fun login(login: Login): Login
}