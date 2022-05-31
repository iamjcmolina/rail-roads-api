package com.il.controller

import com.il.cw.course.controller.response.PageResponse
import com.il.model.Car
import com.il.model.Destination
import com.il.service.DestinationService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import reactor.core.publisher.Mono

@Controller("/destination")
class DestinationController(
    private val destinationService: DestinationService
) {
    @Get("/{id}")
    fun create(@PathVariable id: String): Mono<HttpResponse<Destination>> {
        return destinationService.find(id)
            .map { HttpResponse.created(it) }
    }
    
    @Post
    fun create(@Body destination: Destination): Mono<HttpResponse<Destination>> {
        return destinationService.save(destination)
            .map { HttpResponse.created(it) }
    }
    
    @Delete("/{id}")
    fun delete(@PathVariable id: String): Mono<HttpResponse<Destination>> {
        return destinationService.delete(id)
            .map { HttpResponse.created(it) }
    }
    
    @Get
    fun findAll(): Mono<HttpResponse<PageResponse<Destination>>> {
        return destinationService.findAll()
            .map { HttpResponse.ok(PageResponse.from(it)) }
    }
}