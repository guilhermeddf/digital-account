package com.dock.bank.digitalaccount.core.port.persistence

import com.dock.bank.digitalaccount.core.domain.User

interface UserPersistence {
    fun create(user: User): User
}