package com.dock.bank.digitalaccount.kafka

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.ports.spi.messaging.HolderMessagingPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class KafkaHolderProducer(
    @Value("\${spring.kafka.topic.name}") private val topicName: String,
    private val template: KafkaTemplate<String, String>
): HolderMessagingPort {
    companion object {
        private val logger = LoggerFactory.getLogger(KafkaHolderProducer::class.java)
    }

    override suspend fun publish(holder: Holder) {
        try {
            val messageId = UUID.randomUUID()
            val message = createHolderMessageWithHeaders(messageId, holder)

            template.send(message).whenComplete { response, ex ->
                if (Objects.nonNull(ex)) {
                    logger.error("Failed to send message with id: $messageId")
                    throw Exception(ex)
                }
                logger.info("Success to send event: ${response.recordMetadata}, with messageId: $messageId.")
            }

        } catch (e: Exception) {
            logger.error("Error trying to send message into a kafka topic: $topicName", e)
        }
    }

    private fun createHolderMessageWithHeaders(id: UUID, holder: Holder): Message<Holder> {
        return MessageBuilder.withPayload(holder)
            .setHeader("hash", holder.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(1))
            .setHeader("id", id.toString())
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .setHeader(KafkaHeaders.KEY, id.toString())
            .build()
    }
}