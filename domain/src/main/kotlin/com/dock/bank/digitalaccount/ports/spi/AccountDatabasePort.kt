package com.dock.bank.digitalaccount.ports.spi

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import java.util.UUID
import java.util.Optional

interface AccountDatabasePort {
    suspend fun create(account: Account) : Account
    suspend fun disable(id: UUID, status: Status) : Boolean
    suspend fun block(id: UUID, status: Status): Boolean
    suspend fun enable(id: UUID, status: Status): Boolean
    suspend fun get(id: UUID): Optional<Account>
}