package com.dock.bank.digitalaccount.api.v1.request

import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.math.BigInteger

data class CreateHolderRequest(
    val cpf: String,
    val name: String
)

data class CreateLoginRequest(
    val username: String,
    val password: String
)

data class CreateUserRequest(
    val username: String,
    val password: String,
    val role: String
)

data class CreateTransactionRequest(
    val amount: BigInteger,
    val type: TransactionType,
)
