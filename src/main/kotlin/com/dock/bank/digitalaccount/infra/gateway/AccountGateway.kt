package com.dock.bank.digitalaccount.infra.gateway


import com.dock.bank.digitalaccount.core.domain.Account

interface AccountGateway {
    fun createAccount(holderCpf: String) : Account
}