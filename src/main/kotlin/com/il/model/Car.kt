package com.il.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.*

@DynamoDbBean
class Car(id: UUID?, nameOfCar: String, destination: String, receiver: String) {
        
        constructor(): this (null, "", "", "")
        constructor(nameOfCar: String): this (null, nameOfCar, "", "")
        constructor(nameOfCar: String, destination: String, receiver: String): this (null, nameOfCar, destination, receiver)

        @get: DynamoDbPartitionKey
        @get: DynamoDbAttribute(value = "id")
        var id: UUID = id?: UUID.randomUUID()
        
        @get: DynamoDbAttribute(value = "nameOfCar")
        var nameOfCar: String = nameOfCar
        
        @get: DynamoDbAttribute(value = "destination")
        var destination: String = destination
        
        @get: DynamoDbAttribute(value = "receiver")
        var receiver: String = receiver
}