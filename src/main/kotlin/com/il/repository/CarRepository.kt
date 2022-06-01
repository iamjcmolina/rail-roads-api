package com.il.repository

import com.il.model.Car
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class CarRepository(private val dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
                    @Value("\${dynamodb.car-table-name}") val carTableName: String) {
    
    private val tableSchema = TableSchema.fromBean(Car::class.java)
    private val carTable = dynamoDbEnhancedAsyncClient.table(carTableName, tableSchema)
    
    fun find(id: String): Mono<Car> {
        val key = Key.builder().partitionValue(id).build()
        return carTable.getItem(key).toMono()
    }
    
    fun save(car: Car) : Mono<Car> {
        carTable.putItem(car).get()
        return find(car.id.toString())
    }
    
    fun update(id: String, car: Car) : Mono<Car> {
        val key = Key.builder().partitionValue(id).build()
        var existingCar = carTable.getItem(key).get()
        existingCar.nameOfCar = car.nameOfCar?: existingCar.nameOfCar
        existingCar.destination = car.destination?: existingCar.destination
        existingCar.receiver = car.receiver?: existingCar.receiver
        
        if (existingCar != null) {
            return carTable.updateItem(existingCar).toMono()
        }
        return Mono.empty()
    }

    fun delete(nameOfCar: String): Mono<Car> {
        val key = Key.builder().partitionValue(nameOfCar).build()
        return carTable.deleteItem(key).toMono()
    }
    
    fun findAll(): Mono<Page<Car>> {
        return carTable.scan().toMono()
    }
    
}