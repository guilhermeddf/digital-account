package com.dock.bank.digitalaccount.dynamodb.repository

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.ItemCollection
import com.amazonaws.services.dynamodbv2.document.ScanOutcome
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.GetItemResult
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class DynamoDBRepository(
    private val dynamoDB: AmazonDynamoDB,
    @Value("\${cloud.aws.dynamodb.table-name}") private val tableName: String
) {
    companion object {
        private val logger = LoggerFactory.getLogger(DynamoDBRepository::class.java)
    }

    fun saveItem(items: HashMap<String, AttributeValue>) {

        val putRequest = PutItemRequest().withTableName(tableName).withItem(items)

        try {
            dynamoDB.putItem(putRequest)
            logger.info("Item saved on dynamodb with success.")
        } catch (e:Exception){
            throw Exception("Error trying to put item into dynamodb.", e)
        }
    }

    fun getItemByAttributeValue(value: Pair<String, String>): Optional<Item> {
        val database = DynamoDB(dynamoDB)
        val table = database.getTable(tableName)
        val scan = table.scan()
        return try {
            val response =  scan.first { item -> item.get(value.first) == value.second }
            Optional.of(response)
        } catch (e: NoSuchElementException) {
            Optional.empty()
        }
    }

    fun getItemById(id: UUID): GetItemResult {
        val items = HashMap<String, AttributeValue>()
        items["id"] = AttributeValue(id.toString())

        val getRequest = GetItemRequest().withTableName(tableName).withKey(items)
        try {
            val result = dynamoDB.getItem(getRequest)
            logger.info("Item with id $id was retrieved successfully on dynamodb.")
            return result
        } catch (e: Exception) {
            throw Exception("Error trying to get item into Dynamodb.", e)
        }
    }

    fun deleteItemById(id: UUID) {
        val items = HashMap<String, AttributeValue>()
        items["id"] = AttributeValue(id.toString())

        val deleteRequest = DeleteItemRequest()
            .withTableName(tableName)
            .withKey(items)

        try {
            dynamoDB.deleteItem(deleteRequest)
            logger.info("Item with id $id was deleted successfully on dynamodb.")
        } catch (e: Exception) {
            throw Exception("Error trying to delete item on dynamodb.")
        }
    }

    fun getAll(): ItemCollection<ScanOutcome> {
        val database = DynamoDB(dynamoDB)
        val table = database.getTable(tableName)
        return try {
            table.scan()
        } catch (e: ResourceNotFoundException) {
            throw ResourceNotFoundException("Error getting all holders on dynamodb database.")
        }
    }
}