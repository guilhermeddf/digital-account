package com.dock.bank.digitalaccount.configuration.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Configuration
class SqsConfiguration(@Value("\${cloud.aws.region.static}")private val region: String,
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

