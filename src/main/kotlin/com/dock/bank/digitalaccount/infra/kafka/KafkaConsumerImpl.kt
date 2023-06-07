package com.dock.bank.digitalaccount.infra.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumerImpl {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaConsumerImpl::class.java)
    }
    @KafkaListener(topics = ["\${spring.kafka.topic.name}"], groupId = "group_id")
    fun consume(payload: ConsumerRecord<String, String>) {
        logger.info(payload.value())
    }
}