package com.dock.bank.digitalaccount.infra.credentials

import com.dock.bank.digitalaccount.config.SecretsManagerConfiguration
import com.dock.bank.digitalaccount.infra.storage.S3Connection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class SecretManagerImpl(
    private val smClient: SecretsManagerConfiguration
): CredentialsManager {
    companion object {
        private val logger = LoggerFactory.getLogger(Secre::class.java)
    }

    override suspend fun getCredentials(secretName: String): String {
        try {
            val secretRequest = GetSecretValueRequest().withSecretId(secretName)

            val result = smClient.amazonSM().getSecretValue()
            logger.info("Secrets retrieved with success.")
            return result.secretString()
        } catch (e: Exception) {
            throw Exception("Error trying to retrieve secrets from secrets manager.", e)
        }

    }
}
//https://github.com/codinglk/secretsmanager/blob/master/src/main/java/com/codinglk/secretsmanager/util/SecretsManager.java