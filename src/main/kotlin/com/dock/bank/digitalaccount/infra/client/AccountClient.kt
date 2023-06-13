package com.dock.bank.digitalaccount.infra.client

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import org.springframework.http.ResponseEntity

interface AccountClient {
    fun generateAccount(holder: Holder, auth: String): ResponseEntity<Account>
}