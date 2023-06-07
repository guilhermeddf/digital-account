package com.dock.bank.digitalaccount.core.port.persistence

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import reactor.core.publisher.Mono
import java.time.OffsetDateTime
import java.util.UUID
import java.util.Optional

interface AccountPersistence {
    suspend fun create(account: Account) : Mono<Account>
    suspend fun disable(id: UUID, status: Status) : Boolean
    suspend fun block(id: UUID, status: Status): Boolean
    suspend fun enable(id: UUID, status: Status): Boolean
    suspend fun get(id: UUID): Mono<Account>
}