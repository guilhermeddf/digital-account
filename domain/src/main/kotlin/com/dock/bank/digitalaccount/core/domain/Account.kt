package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.core.exception.DomainException
import java.math.BigInteger
import java.util.*

data class Account (
    val id: UUID,
    val balance: BigInteger,
    val number: String,
    val branch: String,
    val holder: Holder,
    val status: Status,
    val withdrawalLimit: BigInteger
) {

    fun validateAccountIsDisabled() {
        if (Status.DISABLED == status) {
            throw DomainException(message = "Account is disabled.")
        }
    }

    fun validateAccountIsBlocked() {
        if (Status.BLOCKED == status) {
            throw DomainException(message = "Account is blocked.")
        }
    }
}
