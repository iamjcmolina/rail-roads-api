package com.il.services

import com.il.models.InputCars
import com.il.models.OutputCar
import jakarta.inject.Singleton

@Singleton
class OperationsService {
    
    fun categorizeByDestinationAndReceiver(inputCars: InputCars): List<OutputCar>? {
        val outputCars = inputCars
            .cars?.filterNot { it.nameOfCar.equals("Engine") }
            ?.map { OutputCar(inputCars.destinations.indexOf(it.destination) + 1,it.nameOfCar, it.destination, it.receiver) }
            ?.sortedWith(compareBy<OutputCar>({ inputCars.destinations.indexOf(it.destination) }).thenBy { inputCars.receivers.indexOf(it.receiver) })
        
        return outputCars;
    }
}