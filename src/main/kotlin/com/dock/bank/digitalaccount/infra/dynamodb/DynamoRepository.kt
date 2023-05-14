package com.dock.bank.digitalaccount.infra.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.port.persistence.HolderPersistence
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class DynamoRepository(
    private val dynamoDB: AmazonDynamoDB,
    @Value("\${cloud.aws.dynamodb.table-name}") private val tableName: String
): HolderPersistence {


    override suspend fun create(holder: Holder): Optional<Holder> {
        val items = HashMap<String, AttributeValue>()
        items["id"] = AttributeValue(holder.id.toString())

        val putRequest = PutItemRequest().withTableName(tableName).withItem(items)

        try {
            val response = dynamoDB.putItem(putRequest)
            println(response)
        } catch (e:Exception){
            throw Exception("Error trying to put item into DynamoDB.", e)
        }
    }

    override suspend fun findByCpf(holderCpf: String): Optional<Holder> {

    }

    override suspend fun delete(id: UUID) {
        TODO("Not yet implemented")
    }
}