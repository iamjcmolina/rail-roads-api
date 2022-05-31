package com.il.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.*

@DynamoDbBean
class Destination {

    @get: DynamoDbPartitionKey
    @get: DynamoDbAttribute(value = "id")
    var id: UUID = UUID.randomUUID()
    
    @get: DynamoDbAttribute(value = "destination")
    lateinit var destination: String
}
