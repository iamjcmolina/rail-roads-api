package com.il.service

import com.il.model.Car
import com.il.model.InputCars
import com.il.model.OutputCar
import jakarta.inject.Singleton

@Singleton
class OperationsService {

    fun categorizeByDestinationAndReceiver(inputCars: InputCars): List<OutputCar>? {

        val orderByDestinationReceiver = mutableListOf<OutputCar>()

        inputCars.destinations.forEachIndexed { index, orderDestination ->
            val destination = mutableListOf<Car>()

            inputCars.cars.forEach{
                if (it.destination == orderDestination) {
                    destination.add(it)
                }
            }

            inputCars.receivers.forEach{ orderReceiver ->
                destination.forEach{ x ->
                    if(x.receiver == orderReceiver) {
                        orderByDestinationReceiver.add(
                                OutputCar(nameOfCar = x.nameOfCar, destination = x.destination, receiver = x.receiver, classificationTrack = index + 1)
                        )
                    }
                }
            }
        }
        return orderByDestinationReceiver
    }
}