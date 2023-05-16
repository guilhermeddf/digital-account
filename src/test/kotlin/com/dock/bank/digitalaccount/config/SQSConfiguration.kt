package com.dock.bank.digitalaccount.config


import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver


@Configuration
class SQSConfiguration(
    @Value("\${cloud.aws.region.static}")private val region: String,
    @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
    @Value("\${cloud.aws.sqs.end-point.uri}") private val sqsUrl: String
) {

    @Bean
    fun queueMessageHandlerFactory(): QueueMessageHandlerFactory? {
        val factory = QueueMessageHandlerFactory()
        val messageConverter = MappingJackson2MessageConverter()
        messageConverter.isStrictContentTypeMatch = false
        factory.setArgumentResolvers(listOf(PayloadMethodArgumentResolver(messageConverter)))
        return factory
    }


    @Bean
    @Primary
    fun amazonSQS(): AmazonSQSAsync {
        try {
            return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(sqsUrl, region))
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .build()
        } catch (e: Exception){
            throw Exception("Error trying to configure AWS SQS.", e)
        }
    }
}