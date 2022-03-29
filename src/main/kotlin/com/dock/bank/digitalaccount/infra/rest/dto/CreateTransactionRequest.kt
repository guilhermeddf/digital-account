package com.dock.bank.digitalaccount.infra.rest.dto

import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.UUID

data class CreateTransactionRequest(
    val amount: BigInteger,
    val type: TransactionType,
)

data class CreateTransactionResponse(
    val id: UUID,
    val amount: BigInteger,
    val type: TransactionType,
    val createdDate: OffsetDateTime,
)

data class RetrieveTransactionResponse(
    val id: UUID,
    val amount: BigInteger,
    val type: TransactionType,
    val createdDate: OffsetDateTime
)
