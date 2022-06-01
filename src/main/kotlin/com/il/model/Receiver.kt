package com.il.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.*

@DynamoDbBean
class Receiver (
        id: UUID?,
        receiver: String?
) {
    constructor(): this(null, "")

    @get: DynamoDbPartitionKey
    @get: DynamoDbAttribute(value = "id")
    var id: UUID = id?: UUID.randomUUID()

    @get: DynamoDbAttribute(value = "receiver")
    var receiver: String? = receiver
}