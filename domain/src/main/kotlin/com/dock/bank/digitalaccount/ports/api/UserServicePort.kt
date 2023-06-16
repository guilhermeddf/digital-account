package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.User

interface UserServicePort {
    fun create(user: User) : User
}