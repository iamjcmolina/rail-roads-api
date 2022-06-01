package com.il.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.*

@DynamoDbBean
class Car {
        @get: DynamoDbPartitionKey
        @get: DynamoDbAttribute(value = "id")
        var id: UUID = UUID.randomUUID()
        
        @get: DynamoDbAttribute(value = "nameOfCar")
        lateinit var nameOfCar: String
        
        @get: DynamoDbAttribute(value = "destination")
        var destination: String = ""
        
        @get: DynamoDbAttribute(value = "receiver")
        var receiver: String = ""
}