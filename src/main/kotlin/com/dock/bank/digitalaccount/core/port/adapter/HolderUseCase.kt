package com.dock.bank.digitalaccount.core.port.adapter

import com.dock.bank.digitalaccount.core.domain.Holder
import reactor.core.publisher.Mono
import java.util.UUID

interface HolderUseCase {
    suspend fun create(holder: Holder): Mono<Holder>
    suspend fun delete(id: UUID)
    suspend fun get(cpf: String): Holder
}