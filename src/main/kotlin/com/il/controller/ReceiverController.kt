package com.il.controller

import com.il.cw.course.controller.response.PageResponse
import com.il.model.Destination
import com.il.model.Receiver
import com.il.service.ReceiverService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import reactor.core.publisher.Mono

@Controller("/receiver")
class ReceiverController (
        private  val receiverServices: ReceiverService
        ){
    @Get("/{id}")
    fun find(@PathVariable id: String): Mono<HttpResponse<Receiver>> {
        return receiverServices.find(id)
                .map { HttpResponse.ok(it) }
    }

    @Post
    fun create(@Body receiver: Receiver): Mono<HttpResponse<Receiver>> {
        return receiverServices.save(receiver)
                .map { HttpResponse.created(it) }
    }

    @Put("/{id}")
    fun update(@PathVariable id: String, @Body receiver: Receiver): Mono<HttpResponse<Receiver>> {
        return receiverServices.update(id, receiver)
                .map { HttpResponse.ok(it) }
    }

    @Delete("/{id}")
    fun delete(@PathVariable id: String): Mono<HttpResponse<Receiver>> {
        return receiverServices.delete(id)
                .map { HttpResponse.ok(it) }
    }

    @Get
    fun findAll(): Mono<HttpResponse<PageResponse<Receiver>>> {
        return receiverServices.findAll()
                .map { HttpResponse.ok(PageResponse.from(it)) }
    }

}