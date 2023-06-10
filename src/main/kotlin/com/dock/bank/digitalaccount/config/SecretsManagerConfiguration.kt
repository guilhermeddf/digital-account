/* package com.dock.bank.digitalaccount.config


import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AWSSecretsManagerAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecretsManagerConfiguration(
    @Value("\${cloud.aws.region.static}")private val region: String,
    @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
    @Value("\${cloud.aws.secretsmanager.endpoint.uri}") private val smUrl: String
) {

    @Bean
    fun amazonSM(): AWSSecretsManager {
        try {
            return AWSSecretsManagerAsyncClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(smUrl, region))
                .build()
        } catch (e: Exception){
            throw Exception("Error trying to configure AWS Secrets Manager.")
        }
    }
}

 */