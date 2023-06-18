package com.dock.bank.digitalaccount.api.v1.response

import com.dock.bank.digitalaccount.core.domain.Role
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

data class CreateHolderResponse(
    val id: UUID,
    val cpf: String,
    val name: String
)

data class CreateAccountResponse(
    val id: UUID,
    val number: String,
    val branch: String,
    val holderName: String,
    val cpf: String,
    val status: Status
)

data class CreateLoginResponse(
    val username: String,
    val token: String?
)

data class GetAccountResponse(
    val branch: String,
    val number: String,
    val balance: BigInteger
)

data class CreateUserResponse(
    val username: String,
    val password: String,
    val role: Role
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