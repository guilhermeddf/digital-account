package com.dock.bank.digitalaccount.configuration.aws

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Configuration
class SqsConfiguration(@Value("\${cloud.aws.region.static}")private val region: String,
                       @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
                       @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
                       @Value("\${cloud.aws.sqs.end-point.uri}") private val sqsUrl: String
) {

    @Bean
    fun amazonSQS(): SqsClient {
        try {
            return SqsClient.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(sqsUrl))
                .build()
        } catch (e: Exception){
            throw Exception("Error trying to configure AWS SQS.", e)
        }
    }
}

