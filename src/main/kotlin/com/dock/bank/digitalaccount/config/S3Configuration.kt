package com.dock.bank.digitalaccount.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.dock.bank.digitalaccount.infra.storage.S3Connection
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class S3Configuration(
    @Value("\${cloud.aws.region.static}") private val region: String,
    @Value("\${cloud.aws.credentials.access-key}") private val accessKeyId: String,
    @Value("\${cloud.aws.credentials.secret-key}") private val secretAccessKey: String,
    @Value("\${cloud.aws.s3.endpoint.uri}") private val s3Url: String,
) {

    @Bean
    @Primary
    fun amazonS3(): AmazonS3 {
        try {
            return AmazonS3ClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(s3Url, region))
                .build()
        } catch (e: Exception) {
            throw Exception("Error creating s3 storage.")
        }
    }
}