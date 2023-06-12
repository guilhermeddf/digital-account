package com.dock.bank.digitalaccount.core.usecase

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exceptions.ResourceAlreadyExistsException
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.adapter.HolderUseCase
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import java.util.*

class HolderUseCaseImpl(
    private val holderPersistence: HolderPersistence,
) : HolderUseCase {

    override suspend fun create(holder: Holder): Holder {
        holder.validateCpf()

        return holderPersistence.create(holder).orElseThrow {
            throw ResourceAlreadyExistsException(message = "Holder already exists.")
        }
    }

    override suspend fun get(cpf: String): Holder {
        //val response = storage.read(fileName)
        //logger.info(response)
        return holderPersistence.findByCpf(cpf).orElseThrow{
            throw ResourceNotFoundException("Holder not found.")
        }
    }

    override suspend fun delete(id: UUID) {
        return holderPersistence.delete(id)
    }
}