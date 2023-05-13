package com.dock.bank.digitalaccount.infra.credentials

import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.dock.bank.digitalaccount.infra.storage.S3Connection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SecretManagerImpl(
    private val smClient: AWSSecretsManager
): CredentialsManager {
    companion object {
        private val logger = LoggerFactory.getLogger(S3Connection::class.java)
    }

    override suspend fun getCredentials(secretName: String): String {
        val secretRequest = GetSecretValueRequest()
            .withSecretId(secretName)

        try {
            val result = smClient.getSecretValue(secretRequest)
            logger.info("Secrets retrieved with success.")
            return result.secretString
        } catch (e: Exception) {
            throw Exception("Error trying to retrieve secrets from secrets manager.")
        }

    }
}