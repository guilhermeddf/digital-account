package com.dock.bank.digitalaccount.utils

import com.dock.bank.digitalaccount.core.domain.TransactionType
import com.dock.bank.digitalaccount.infra.rest.dto.CreateHolderRequest
import com.dock.bank.digitalaccount.infra.rest.dto.CreateTransactionRequest
import java.math.BigInteger

fun buildCreateHolderRequest(
    cpf: String = "00000000000",
    name: String = "Guilherme Dutra Diniz de Freitas"
) = CreateHolderRequest(
     cpf, name
)

fun buildCreateTransactionRequest(
    amount: BigInteger = BigInteger.valueOf(100),
    type: TransactionType = TransactionType.CREDIT,
    ) = CreateTransactionRequest(
    amount, type
)