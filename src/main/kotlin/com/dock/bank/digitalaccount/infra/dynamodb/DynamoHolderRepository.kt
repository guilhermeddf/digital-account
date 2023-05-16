package com.dock.bank.digitalaccount.infra.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.dock.bank.digitalaccount.infra.secretsManager.converter.JsonConverter
import com.dock.bank.digitalaccount.infra.postgres.model.HolderTable
import com.dock.bank.digitalaccount.infra.sqs.SQSConsumerImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class DynamoHolderRepository(
    private val dynamoDB: AmazonDynamoDB,
    private val jsonConverter: JsonConverter,
    @Value("\${cloud.aws.dynamodb.table-name}") private val tableName: String
) {
    companion object {
        private val logger = LoggerFactory.getLogger(SQSConsumerImpl::class.java)
    }

    suspend fun save(holder: HolderTable): HolderTable {
        val items = HashMap<String, AttributeValue>()
        items["id"] = AttributeValue(holder.id.toString())
        items["name"] = AttributeValue(holder.name)
        items["cpf"] = AttributeValue(holder.cpf)

        val putRequest = PutItemRequest().withTableName(tableName).withItem(items)

        try {
            dynamoDB.putItem(putRequest)
            return holder
        } catch (e:Exception){
            throw Exception("Error trying to put item into DynamoDB.", e)
        }
    }

    suspend fun findHolderByCpf(holderCpf: String): Optional<HolderTable> {
        val database = DynamoDB(dynamoDB)
        val table = database.getTable(tableName)

        return try {
            val scan = table.scan()
            val result = scan.first { item -> item.get("cpf") == holderCpf}.toJSON()
            Optional.of(jsonConverter.toHolderTable(result))
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }

    suspend fun existsById(id: UUID): Boolean {
        val items = HashMap<String, AttributeValue>()
        items["id"] = AttributeValue(id.toString())

        val getRequest = GetItemRequest().withTableName(tableName).withKey(items)
        try {
            val result = dynamoDB.getItem(getRequest)
            return result.item != null
        } catch (e: Exception) {
            throw Exception("Error trying to get item into DynamoDB.", e)
        }
    }

    suspend fun deleteById(id: UUID) {
        val items = HashMap<String, AttributeValue>()
        items["id"] = AttributeValue(id.toString())

        val deleteRequest = DeleteItemRequest()
            .withTableName(tableName)
            .withKey(items)

        try {
            dynamoDB.deleteItem(deleteRequest)
        } catch (e: Exception) {
            throw Exception("Error trying to delete item on dynamoDB.")
        }
    }
}