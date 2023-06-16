package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Status
import java.util.UUID

interface AccountServicePort {
    suspend fun create(holderCpf: String) : Account
    suspend fun disable(id: UUID, status: Status) : Boolean
    suspend fun block(id: UUID, status: Status): Boolean
    suspend fun enable(id: UUID, status: Status): Boolean
    suspend fun get(id: UUID): Account
}