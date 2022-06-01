package com.il.controller

import com.il.cw.course.controller.response.PageResponse
import com.il.model.Car
import com.il.service.CarService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import reactor.core.publisher.Mono

@Controller("/car")
class CarController(
    private val carService: CarService
) {
    @Get("/{id}")
    fun find(@PathVariable id: String): Mono<HttpResponse<Car>> {
        return carService.find(id)
            .map { HttpResponse.ok(it) }
    }
    
    @Post
    fun create(@Body car: Car): Mono<HttpResponse<Car>> {
        return carService.save(car)
            .map { HttpResponse.created(it) }
    }
    
    @Patch("/{id}")
    fun update(@PathVariable id: String, @Body destination: Car): Mono<HttpResponse<Car>> {
        return carService.update(id, destination)
            .map { HttpResponse.ok(it) }
    }
    
    @Delete("/{id}")
    fun delete(@PathVariable id: String): Mono<HttpResponse<Car>> {
        return carService.delete(id)
            .map { HttpResponse.ok(it) }
    }
    
    @Get
    fun findAll(): Mono<HttpResponse<PageResponse<Car>>> {
        return carService.findAll()
            .map { HttpResponse.ok(PageResponse.from(it)) }
    }
}