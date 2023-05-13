package com.dock.bank.digitalaccount.config


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import java.net.URI

@Configuration
class SecretsManagerConfiguration(
    @Value("\${cloud.aws.region.static}")private val region: String,
    @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
    @Value("\${cloud.aws.sqs.end-point.uri}") private val sqsUrl: String
) {

    @Bean
    fun amazonSM(): SecretsManagerClient {
        return SecretsManagerClient.builder()
            .region(Region.of(region))
            .credentialsProvider(ProfileCredentialsProvider.builder().profileName("localstack").build())
            .endpointOverride(URI.create(sqsUrl))
            .build()
    }
}