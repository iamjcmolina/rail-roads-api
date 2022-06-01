package com.il.service

import com.il.model.Receiver
import com.il.repository.ReceiverRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class ReceiverService (
        private val receiverRepository: ReceiverRepository
        ) {
    fun find(id: String): Mono<Receiver> {
        return receiverRepository.find(id)
    }

    fun save(destination: Receiver): Mono<Receiver> {
        return receiverRepository.save(destination)
    }

    fun update(id: String, receiver: Receiver): Mono<Receiver> {
        return receiverRepository.update(id, receiver)
    }

    fun delete(id: String): Mono<Receiver> {
        return receiverRepository.delete(id)
    }

    fun findAll(): Mono<Page<Receiver>> {
        return receiverRepository.findAll()
    }

}