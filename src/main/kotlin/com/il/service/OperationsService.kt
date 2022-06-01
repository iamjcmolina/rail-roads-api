package com.il.service

import com.il.model.InputCars
import com.il.model.OutputCar
import jakarta.inject.Singleton

@Singleton
class OperationsService {
    
    fun categorizeByDestinationAndReceiver(inputCars: InputCars): List<OutputCar>? {
        val destinations = inputCars.cars.map { car -> car.destination }.toSet()
        val receivers = inputCars.cars.map { car -> car.receiver }.toSet()
        val outputCars = inputCars
            .cars?.filterNot { it.nameOfCar.equals("Engine") }
            ?.map { OutputCar(destinations.indexOf(it.destination) + 1, it.nameOfCar, it.destination, it.receiver) }
            ?.sortedWith(compareBy<OutputCar>({ destinations.indexOf(it.destination) }).thenBy { receivers.indexOf(it.receiver) })
        
        return outputCars;
    }
}