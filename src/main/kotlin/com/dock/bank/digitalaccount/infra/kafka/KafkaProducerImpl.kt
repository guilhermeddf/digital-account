package com.dock.bank.digitalaccount.infra.kafka

import com.dock.bank.digitalaccount.core.port.messaging.BrokerProducer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducerImpl(
    @Value("\${spring.kafka.topic.name}") private val topicName: String,
    private val template: KafkaTemplate<String, String>
): BrokerProducer {
    companion object {
        private val logger = LoggerFactory.getLogger(KafkaProducerImpl::class.java)
    }
    override suspend fun publish(message: String) {
        try {
            template.send(topicName, message)
        } catch (e: Exception) {
            logger.error("Error trying to send message into a kafka topic: $topicName")
        }
    }
}