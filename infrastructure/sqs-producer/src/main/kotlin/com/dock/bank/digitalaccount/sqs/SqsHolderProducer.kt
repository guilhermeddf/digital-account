package com.dock.bank.digitalaccount.sqs

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.ports.spi.messaging.HolderMessagingPort
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

@Service
@Primary
class SqsHolderProducer(
    private val sqsClient: SqsClient,
    @Value("\${cloud.aws.sqs.end-point.uri}") private val sqsUrl: String,
    private val objectMapper: ObjectMapper
): HolderMessagingPort {
    companion object {
        private val logger = LoggerFactory.getLogger(SqsHolderProducer::class.java)
    }

    override suspend fun publish(holder: Holder) {
        try {
            val sendMessageReq = SendMessageRequest.builder()
                .queueUrl(sqsUrl)
                .messageBody(objectMapper.writeValueAsString(holder)).build()
            val response = sqsClient.sendMessage(sendMessageReq)
            logger.info("Message with ${response.messageId()} publish with success.")
        } catch (e: Exception){
            throw Exception("Error trying to publish queue message.", e)
        }
    }
}