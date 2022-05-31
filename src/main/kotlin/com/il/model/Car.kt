package com.il.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import java.util.*

@DynamoDbBean
data class Car(
        var id: UUID = UUID.randomUUID(),
        var nameOfCar: String,
        var destination: String = "",
        var receiver: String = ""
)