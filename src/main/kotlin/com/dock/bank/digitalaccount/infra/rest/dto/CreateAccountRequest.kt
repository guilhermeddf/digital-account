package com.dock.bank.digitalaccount.infra.rest.dto

import com.dock.bank.digitalaccount.core.domain.Status
import java.math.BigInteger
import java.util.UUID


data class CreateAccountResponse(
    val id: UUID,
    val number: String,
    val branch: String,
    val holderName: String,
    val status: Status
)

data class GetAccountResponse(
    val branch: String,
    val number: String,
    val balance: BigInteger
)