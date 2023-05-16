package com.dock.bank.digitalaccount.core.port.adapter

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Credentials
import com.dock.bank.digitalaccount.core.domain.Status
import java.util.UUID

interface AccountUseCase {
    suspend fun create(holderCpf: String) : Account
    suspend fun disable(id: UUID, status: Status) : Boolean
    suspend fun block(id: UUID, status: Status): Boolean
    suspend fun enable(id: UUID, status: Status): Boolean
    suspend fun get(id: UUID, credentials: Credentials): Account
}