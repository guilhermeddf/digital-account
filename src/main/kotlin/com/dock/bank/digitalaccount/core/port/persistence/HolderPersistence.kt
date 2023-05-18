package com.dock.bank.digitalaccount.core.port.persistence

import com.dock.bank.digitalaccount.core.domain.Holder
import reactor.core.publisher.Mono
import java.util.UUID
import java.util.Optional

interface HolderPersistence {
    suspend fun create(holder: Holder) : Mono<Optional<Holder>>
    suspend fun findByCpf(holderCpf: String) : Optional<Holder>
    suspend fun delete(id: UUID)
}