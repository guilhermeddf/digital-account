package com.dock.bank.digitalaccount.infra.gateway


import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder

interface AccountGateway {
    suspend fun generateAccount(holder: Holder) : Account?
}