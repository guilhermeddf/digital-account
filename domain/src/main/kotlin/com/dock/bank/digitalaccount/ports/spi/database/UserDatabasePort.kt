package com.dock.bank.digitalaccount.ports.spi.database

import com.dock.bank.digitalaccount.core.domain.User

interface UserDatabasePort {
    fun create(user: User): User
}