package com.dock.bank.digitalaccount.infra.gateway

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.infra.client.AccountClient
import com.dock.bank.digitalaccount.infra.secretsManager.CredentialsManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class AccountGatewayImpl(
    private val accountClient: AccountClient,
    @Value("\${account-service.auth}") private val auth: String,
    private val credentialsManager: CredentialsManager
): AccountGateway {
    override suspend fun generateAccount(holder: Holder): Account? {
        val credentials = credentialsManager.getCredentials(auth)
        return accountClient.generateAccount(holder, credentials.password).body
    }
}