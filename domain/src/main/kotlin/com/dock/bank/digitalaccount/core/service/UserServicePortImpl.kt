package com.dock.bank.digitalaccount.core.service


import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.ports.api.UserServicePort
import com.dock.bank.digitalaccount.ports.spi.UserDatabasePort


class UserServicePortImpl(
    private val userDatabasePort: UserDatabasePort
): UserServicePort {

    override fun create(user: User) : User {
        return userDatabasePort.create(user)
    }
}