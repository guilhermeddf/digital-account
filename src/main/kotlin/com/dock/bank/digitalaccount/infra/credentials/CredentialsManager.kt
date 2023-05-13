package com.dock.bank.digitalaccount.infra.credentials

interface CredentialsManager {
    suspend fun getCredentials(secretName: String): String
}