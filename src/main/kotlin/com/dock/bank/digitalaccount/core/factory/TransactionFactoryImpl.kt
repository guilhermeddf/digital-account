package com.dock.bank.digitalaccount.core.factory

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.core.port.adapter.TransactionFactory
import com.dock.bank.digitalaccount.core.port.persistence.AccountPersistence
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

class TransactionFactoryImpl(
    private val accountPersistence: AccountPersistence
) : TransactionFactory{
    override suspend fun generateTransaction(
        amount: BigInteger,
        type: TransactionType,
        accountId: UUID
    ): Transaction {

        return Transaction(
            id = UUID.randomUUID(),
            amount = amount,
            type = type,
            createdDate = OffsetDateTime.now(),
            account = fillAccountById(accountId)
        )
    }

    private suspend fun fillAccountById(id: UUID) : Account {
        return accountPersistence.get(id)
            .switchIfEmpty(Mono.error(ResourceNotFoundException(message = "Account not found.")))

    }
}