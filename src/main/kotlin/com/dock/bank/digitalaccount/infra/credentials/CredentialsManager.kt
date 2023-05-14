package com.dock.bank.digitalaccount.infra.credentials

import com.dock.bank.digitalaccount.core.domain.Credentials

interface CredentialsManager {
    suspend fun getCredentials(secretName: String): Credentials
}