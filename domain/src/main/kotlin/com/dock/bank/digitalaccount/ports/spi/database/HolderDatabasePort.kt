package com.dock.bank.digitalaccount.ports.spi.database

import com.dock.bank.digitalaccount.core.domain.Holder
import java.util.UUID
import java.util.Optional

interface HolderDatabasePort {
    suspend fun create(holder: Holder) : Optional<Holder>
    suspend fun findByCpf(holderCpf: String) : Optional<Holder>
    suspend fun delete(id: UUID)
    suspend fun getAll(): List<Holder>
}