package com.dock.bank.digitalaccount.utils

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.UUID

fun buildHolder(
    id: UUID = UUID.randomUUID(),
    cpf: String = "00000000000",
    name: String = "Guilherme Dutra Diniz de Freitas"
) = Holder(
    id = id,
    cpf = cpf,
    name = name
)

fun buildAccount(
     id: UUID = UUID.randomUUID(),
     balance: BigInteger = BigInteger.valueOf(0),
     number: String = "45450",
     branch: String = "1212",
     holder: Holder = buildHolder(),
     status: Status = Status.ACTIVATED,
     withdrawalLimit: BigInteger = BigInteger.valueOf(200000)
) = Account(
    id = id,
    balance = balance,
    number = number,
    branch = branch,
    holder = holder,
    status = status,
    withdrawalLimit = withdrawalLimit
)

fun buildTransaction(
    id: UUID = UUID.randomUUID(),
    amount: BigInteger = BigInteger.valueOf(1000),
    type: TransactionType = TransactionType.CREDIT,
    createdDate: OffsetDateTime = OffsetDateTime.now(),
    account: Account = buildAccount()
) = Transaction(
    id = id,
    amount = amount,
    type = type,
    createdDate = createdDate,
    account = account
)