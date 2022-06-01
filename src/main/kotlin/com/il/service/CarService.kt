package com.il.service

import com.il.model.Car
import com.il.repository.CarRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class CarService(
    private val carRepository: CarRepository
) {
    
    fun find(id: String): Mono<Car> {
        return carRepository.find(id)
    }
    
    fun save(car: Car): Mono<Car> {
        return carRepository.save(car)
    }
    
    fun update(id: String, car: Car): Mono<Car> {
        return carRepository.update(id, car)
    }
    
    fun delete(id: String): Mono<Car> {
        return carRepository.delete(id)
    }
    
    fun findAll(): Mono<Page<Car>> {
        return carRepository.findAll()
    }
}