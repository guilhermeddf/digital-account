package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.User

interface RegisterService {
    fun register(user: User) : User
}