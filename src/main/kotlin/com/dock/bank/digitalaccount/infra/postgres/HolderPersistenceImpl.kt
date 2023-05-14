package com.dock.bank.digitalaccount.infra.postgres

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import com.dock.bank.digitalaccount.infra.postgres.converter.toEntity
import com.dock.bank.digitalaccount.infra.postgres.converter.toTable
import com.dock.bank.digitalaccount.infra.postgres.repository.HolderRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional

@Repository
class HolderPersistenceImpl (
    private val holderRepository: HolderRepository
) : HolderPersistence {

    override suspend fun create(holder: Holder): Optional<Holder> {
        val storedHolder = holderRepository.findHolderByCpf(holder.cpf)
        return if (storedHolder.isEmpty) {
            Optional.of(holderRepository.save(holder.toTable()).toEntity())
        } else {
            Optional.empty<Holder>()
        }
    }

    override suspend fun findByCpf(holderCpf: String): Optional<Holder> {
        val storedHolder = holderRepository.findHolderByCpf(holderCpf)
        return if (storedHolder.isEmpty) {
            Optional.empty<Holder>()
        } else {
            Optional.of(storedHolder.get().toEntity())
        }
    }

    override suspend fun delete(id: UUID)  {
        if (holderRepository.existsById(id)) {
            return holderRepository.deleteById(id)
        }
    }
}