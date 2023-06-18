package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.math.BigInteger
import java.util.*

interface TransactionFactoryServicePort {
    suspend fun generateTransaction(
        amount: BigInteger,
        type: TransactionType,
        accountId: UUID
    ) : Transaction
}