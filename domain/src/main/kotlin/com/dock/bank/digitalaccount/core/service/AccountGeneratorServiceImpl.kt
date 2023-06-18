package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.domain.Status
import com.dock.bank.digitalaccount.ports.api.AccountGeneratorServicePort
import java.math.BigInteger
import java.util.*
import kotlin.random.Random

class AccountGeneratorServiceImpl : AccountGeneratorServicePort {

    companion object {
        const val MIN_ACCOUNT_RANDOM_NUMBER = 10000
        const val MAX_ACCOUNT_RANDOM_NUMBER = 99999
        const val MIN_BRANCH_RANDOM_NUMBER = 1000
        const val MAX_BRANCH_RANDOM_NUMBER = 9999
    }

     override fun generateAccount(holder: Holder) : Account {
        val account = Account(
            id = UUID.randomUUID(),
            balance = BigInteger.ZERO,
            number = generateAccountNumber(),
            branch = generateBranchNumber(),
            holder = holder,
            status = Status.ACTIVATED,
            withdrawalLimit = BigInteger.valueOf(200000)
        )
         return account
    }

    private fun generateAccountNumber() : String {
        val random = Random(System.currentTimeMillis())
        return (random.nextInt(MAX_ACCOUNT_RANDOM_NUMBER - MIN_ACCOUNT_RANDOM_NUMBER) + MIN_ACCOUNT_RANDOM_NUMBER).toString()
    }

     private fun generateBranchNumber() : String {
        val random = Random(System.currentTimeMillis())
        return (random.nextInt(MAX_BRANCH_RANDOM_NUMBER - MIN_BRANCH_RANDOM_NUMBER) + MIN_BRANCH_RANDOM_NUMBER).toString()
    }
}