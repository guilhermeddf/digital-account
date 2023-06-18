package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.ResourceAlreadyExistsException
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.ports.api.HolderServicePort
import com.dock.bank.digitalaccount.ports.spi.HolderDatabasePort
import java.util.*

class HolderServicePortImpl(
    private val holderDatabasePort: HolderDatabasePort,
) : HolderServicePort {

    override suspend fun create(holder: Holder): Holder {
        holder.validateCpf()
        return holderDatabasePort.create(holder).orElseThrow {
            throw ResourceAlreadyExistsException(message = "Holder already exists.")
        }
    }

    override suspend fun get(cpf: String): Holder = holderDatabasePort.findByCpf(cpf).orElseThrow {
            throw ResourceNotFoundException("Holder not found.")
    }

    override suspend fun getAll() = holderDatabasePort.getAll()
    override suspend fun delete(id: UUID) = holderDatabasePort.delete(id)
}