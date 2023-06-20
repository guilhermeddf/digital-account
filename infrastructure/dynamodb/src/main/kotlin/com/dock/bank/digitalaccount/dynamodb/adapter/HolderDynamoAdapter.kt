package com.dock.bank.digitalaccount.dynamodb.adapter

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.GeneralException
import com.dock.bank.digitalaccount.ports.spi.HolderDatabasePort
import com.dock.bank.digitalaccount.dynamodb.repository.DynamoDBRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class HolderDynamoAdapter(
    private val dynamoDBRepository: DynamoDBRepository,
    private val objectMapper: ObjectMapper
): HolderDatabasePort {
    companion object {
        private val logger = LoggerFactory.getLogger(HolderDynamoAdapter::class.java)
    }

    override suspend fun create(holder: Holder): Optional<Holder> {
        logger.info("Searching holder with cpf ${holder.cpf} on dynamoDB database.")
        val dynamoStoredHolder = findByCpf(holder.cpf)
        return if (dynamoStoredHolder.isEmpty) {
            logger.info("Creating holder with id ${holder.id} on postgres database.")

            val items = HashMap<String, AttributeValue>()
            items["id"] = AttributeValue(holder.id.toString())
            items["name"] = AttributeValue(holder.name)
            items["cpf"] = AttributeValue(holder.cpf)

            Optional.of(dynamoDBRepository.saveItem(items))
            return Optional.of(holder)
        } else {
            logger.error("Holder with id: ${holder.id} and username: ${holder.name} its already on postgres database.")
            Optional.empty()
        }
    }

    override suspend fun findByCpf(holderCpf: String): Optional<Holder> {
        val pair = Pair("cpf", holderCpf)

        val response = dynamoDBRepository.getItemByAttributeValue(pair)
        if(response.isPresent) {
            try {
                return Optional.of(objectMapper.readValue(response.get().toJSON(), Holder::class.java))
            } catch (e: Exception) {
                throw GeneralException("Error converting dynamoDB holder object.")
            }
        }
        return Optional.empty()
    }

    override suspend fun delete(id: UUID) {
        logger.info("Deleting holder with id: $id")
        dynamoDBRepository.deleteItemById(id)
    }

    override suspend fun getAll(): List<Holder> {
        logger.info("Getting all holders on dynamoDB database.")
        return dynamoDBRepository.getAll().map { objectMapper.readValue(it.toJSON(), Holder::class.java) }
    }
}