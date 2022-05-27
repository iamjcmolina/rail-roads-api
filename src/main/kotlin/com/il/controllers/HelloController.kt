package com.il.controllers

import com.il.services.OperationsService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*

@Controller("/hello")
class HelloController (
    private val operations: OperationsService
) {
    
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    fun getHello(): HttpResponse<Any> {
        data class Data(val data: String)
        val httpResponse: HttpResponse<Any>
        httpResponse = HttpResponse.status<Any>(HttpStatus.OK).body<Any>(Data("Hello world"))
        return httpResponse
    }
    
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    fun postHello(): HttpResponse<Any> {
        data class Data(val data: String)
        val httpResponse: HttpResponse<Any>
        httpResponse = HttpResponse.status<Any>(HttpStatus.OK).body<Any>(Data("Hello world"))
        return httpResponse
    }
    
}