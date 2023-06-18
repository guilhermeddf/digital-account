package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.core.exception.DomainException
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*

data class Transaction (
    val id: UUID,
    val amount: BigInteger,
    val type: TransactionType,
    val createdDate: OffsetDateTime,
    val account: Account
) {

    fun accountDeposit() : Account {
        return account.copy(balance = account.balance + amount)
    }

    fun accountWithdraw() : Account {
        validateAccountBalance()
        return account.copy(balance = account.balance - amount)
    }

    private fun validateAccountBalance() {
        if ( account.balance < amount) {
            throw DomainException(message = "Insufficient balance.")
        }
    }
}