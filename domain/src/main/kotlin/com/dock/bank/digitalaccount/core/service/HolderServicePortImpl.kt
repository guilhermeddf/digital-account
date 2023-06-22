package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.ResourceAlreadyExistsException
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.ports.api.HolderServicePort
import com.dock.bank.digitalaccount.ports.spi.database.HolderDatabasePort
import com.dock.bank.digitalaccount.ports.spi.messaging.HolderMessagingPort
import com.dock.bank.digitalaccount.ports.spi.messaging.HolderReceiver
import java.util.*

class HolderServicePortImpl(
    private val holderDatabasePort: HolderDatabasePort,
    private val holderMessagingPort: HolderMessagingPort,
    private val holderReceiver: HolderReceiver
) : HolderServicePort {

    override suspend fun create(holder: Holder): Holder {
        holder.validateCpf()
        val response = holderDatabasePort.create(holder).orElseThrow {
            throw ResourceAlreadyExistsException(message = "Holder already exists.")
        }
        holderMessagingPort.publish(holder)
        val responseQueue = holderReceiver.receive()
        return response
    }

    override suspend fun get(cpf: String): Holder = holderDatabasePort.findByCpf(cpf).orElseThrow {
            throw ResourceNotFoundException("Holder not found.")
    }

    override suspend fun getAll() = holderDatabasePort.getAll()
    override suspend fun delete(id: UUID) = holderDatabasePort.delete(id)
}