package com.il
import com.il.models.Car
import com.il.models.InputCars
import com.il.services.OperationsService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest
class OperationsServiceTest {

    @Inject
    lateinit var operationsService: OperationsService
    
    @Test
    fun test() {
        val destinations = listOf("Houston", "Chicago", "LA")
        val receivers = listOf("UPS","FedEx","Old Dominion")
        val cars = listOf(
            Car("Engine"),
            Car("Box Car 1", "Houston", "FedEx"),
            Car("Box Car 2", "Chicago", "FedEx"),
            Car("Box Car 3", "Houston", "UPS"),
            Car("Box Car 4", "LA", "Old Dominion"),
            Car("Box Car 5", "LA", "FedEx"),
            Car("Box Car 6", "Houston", "Old Dominion")
        )
        val inputCars = InputCars(destinations, receivers, cars)
        
        val sortedCars = operationsService.categorizeByDestinationAndReceiver(inputCars)
        
        Assertions.assertTrue(sortedCars?.get(0)?.nameOfCar.equals("Box Car 3"))
        Assertions.assertTrue(sortedCars?.get(1)?.nameOfCar.equals("Box Car 1"))
        Assertions.assertTrue(sortedCars?.get(2)?.nameOfCar.equals("Box Car 6"))
        Assertions.assertTrue(sortedCars?.get(3)?.nameOfCar.equals("Box Car 2"))
        Assertions.assertTrue(sortedCars?.get(4)?.nameOfCar.equals("Box Car 5"))
        Assertions.assertTrue(sortedCars?.get(5)?.nameOfCar.equals("Box Car 4"))
    }

}