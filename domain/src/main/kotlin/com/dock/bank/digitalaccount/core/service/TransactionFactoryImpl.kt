package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.ports.api.TransactionFactoryServicePort
import com.dock.bank.digitalaccount.ports.spi.database.AccountDatabasePort
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

class TransactionFactoryImpl(
    private val accountPersistence: AccountDatabasePort
) : TransactionFactoryServicePort {
    override suspend fun generateTransaction(amount: BigInteger, type: TransactionType, accountId: UUID) =
        Transaction(
            id = UUID.randomUUID(),
            amount = amount,
            type = type,
            createdDate = OffsetDateTime.now(),
            account = fillAccountById(accountId)
        )

    private suspend fun fillAccountById(id: UUID) = accountPersistence.get(id).orElseThrow {
            throw ResourceNotFoundException(message = "Account not found.")
        }
}