package com.dock.bank.digitalaccount.configuration.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DynamoDBConfiguration(
    @Value("\${cloud.aws.region.static}") private val region: String,
    @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
    @Value("\${cloud.aws.dynamodb.endpoint.uri}") private val dynamoUrl: String) {

    @Bean
    fun dynamoDB(): AmazonDynamoDB {
        try {
            return AmazonDynamoDBAsyncClientBuilder.standard()
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(dynamoUrl, region))
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .build()
        } catch (e: Exception){
            throw Exception("Error trying to configure AWS DynamoDB.", e)
        }
    }
}
