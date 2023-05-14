package com.dock.bank.digitalaccount.infra.credentials

import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.dock.bank.digitalaccount.config.SecretsManagerConfiguration
import com.dock.bank.digitalaccount.core.domain.Credentials
import com.dock.bank.digitalaccount.infra.credentials.converter.JsonConverter
import com.dock.bank.digitalaccount.infra.storage.S3Connection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class SecretManagerImpl(
    private val secretManagerClient: AWSSecretsManager,
    private val jsonConverter: JsonConverter
): CredentialsManager {
    companion object {
        private val logger = LoggerFactory.getLogger(SecretManagerImpl::class.java)
    }

    override suspend fun getCredentials(secretName: String): Credentials {
        try {
            val secretRequest = GetSecretValueRequest().withSecretId(secretName)
            val result = secretManagerClient.getSecretValue(secretRequest)
            logger.info("Secrets retrieved with success.")
            return jsonConverter.toEntity(result.secretString)
        } catch (e: Exception) {
            throw Exception("Error trying to retrieve secrets from secrets manager.", e)
        }
    }
}