package com.il.repository

import com.il.model.Destination
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class DestinationRepository(
    private val dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
    @Value("\${dynamodb.destination-table-name}") val destinationTableName: String
) {
    
    private val tableSchema = TableSchema.fromBean(Destination::class.java)
    private val destinationTable = dynamoDbEnhancedAsyncClient.table(destinationTableName, tableSchema)
    
    fun find(id: String): Mono<Destination> {
        val key = Key.builder().partitionValue(id).build()
        return destinationTable.getItem(key).toMono()
    }
    
    fun save(destination: Destination) : Mono<Destination> {
        destinationTable.putItem(destination).get()
        return find(destination.id.toString())
    }
    
    fun update(id: String, destination: Destination) : Mono<Destination> {
        val key = Key.builder().partitionValue(id).build()
        var existingDestination = destinationTable.getItem(key).get()
        existingDestination.destination = destination.destination?: existingDestination.destination
        
        if (existingDestination != null) {
            destinationTable.updateItem(existingDestination).get()
        }
        return find(id)
    }

    fun delete(id: String): Mono<Destination> {
        val key = Key.builder().partitionValue(id).build()
        return destinationTable.deleteItem(key).toMono()
    }
    
    fun findAll(): Mono<Page<Destination>> {
        return destinationTable.scan().toMono()
    }
}