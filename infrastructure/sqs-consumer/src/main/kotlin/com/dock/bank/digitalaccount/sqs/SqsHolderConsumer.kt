package com.dock.bank.digitalaccount.sqs

import com.dock.bank.digitalaccount.ports.spi.messaging.HolderReceiver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest


@Component
class SqsHolderConsumer(
    private val sqsClient: SqsClient,
    @Value("\${cloud.aws.sqs.end-point.uri}") private val queueUrl: String
): HolderReceiver {
    companion object {
        private val logger = LoggerFactory.getLogger(SqsHolderConsumer::class.java)
    }

    override fun receive(): List<String> {
        try {
            val receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .build()
            val messages = sqsClient.receiveMessage(receiveMessageRequest).messages()
            deleteMessage(messages)
            return messages.map { message -> message.body() }
        } catch (e: Exception) {
            throw Exception("Error trying to receive message from queue.")
        }
    }

    private fun deleteMessage(messages: List<Message>) {
        messages.forEach {
            val deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(it.receiptHandle())
                .build()
            sqsClient.deleteMessage(deleteMessageRequest)
        }
    }
}