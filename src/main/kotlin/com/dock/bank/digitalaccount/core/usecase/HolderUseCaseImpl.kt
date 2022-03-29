package com.dock.bank.digitalaccount.core.usecase

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ResourceAlreadyExistsException
import com.dock.bank.digitalaccount.core.port.adapter.HolderUseCase
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class HolderUseCaseImpl(
    private val holderPersistence: HolderPersistence
) : HolderUseCase {
    override suspend fun create(holder: Holder): Holder {
        holder.validateCpf()
        return holderPersistence.create(holder).orElseThrow {
            throw ResourceAlreadyExistsException(message = "Holder already exists.")
        }
    }

    override suspend fun delete(id: UUID) {
        return holderPersistence.delete(id)
    }
}