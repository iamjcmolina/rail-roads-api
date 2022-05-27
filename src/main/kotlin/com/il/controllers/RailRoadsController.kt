package com.il.controllers

import com.il.models.InputCars
import com.il.services.OperationsService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*

@Controller("/operations")
class RailRoadsController (
    private val operations: OperationsService
) {

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    fun processCars(@Body inputCars: InputCars): HttpResponse<Any> {
        
        val outputCars = operations.categorizeByDestinationAndReceiver(inputCars)

        val httpResponse: HttpResponse<Any>
        httpResponse = HttpResponse.status<Any>(HttpStatus.OK).body<Any>(outputCars)

        return httpResponse
    }
    
}