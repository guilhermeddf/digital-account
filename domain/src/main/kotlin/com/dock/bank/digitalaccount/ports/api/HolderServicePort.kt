package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.Holder
import java.util.UUID

interface HolderServicePort {
    suspend fun create(holder: Holder): Holder
    suspend fun delete(id: UUID)
    suspend fun get(cpf: String): Holder
    suspend fun getAll(): List<Holder>
}