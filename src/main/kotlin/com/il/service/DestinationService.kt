package com.il.service

import com.il.model.Destination
import com.il.repository.DestinationRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class DestinationService(
    private val destinationRepository: DestinationRepository
) {
    
    fun find(id: String): Mono<Destination> {
        return destinationRepository.find(id)
    }
    
    fun save(destination: Destination): Mono<Destination> {
        return destinationRepository.save(destination)
    }
    
    fun delete(id: String): Mono<Destination> {
        return destinationRepository.delete(id)
    }
    
    fun findAll(): Mono<Page<Destination>> {
        return destinationRepository.findAll()
    }
}