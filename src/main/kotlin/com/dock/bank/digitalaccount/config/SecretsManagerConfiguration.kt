package com.dock.bank.digitalaccount.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SecretsManagerConfiguration(
    @Value("\${cloud.aws.region.static}")private val region: String,
    @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
    @Value("\${cloud.aws.sqs.end-point.uri}") private val sqsUrl: String
) {

    @Bean
    fun amazonSM(): AWSSecretsManager? {
        return AWSSecretsManagerClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(sqsUrl, region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
            .build()
    }
}