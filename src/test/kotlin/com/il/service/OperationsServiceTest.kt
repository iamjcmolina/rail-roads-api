package com.il
import com.il.model.Car
import com.il.model.InputCars
import com.il.service.OperationsService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import java.util.*

@MicronautTest
class OperationsServiceTest {

    @Inject
    lateinit var operationsService: OperationsService
    
    @Test
    fun test() {
        val destinations = listOf("Houston", "Chicago", "LA")
        val receivers = listOf("UPS","FedEx","Old Dominion")
        val cars = listOf(
            Car(UUID.randomUUID(), "Engine"),
            Car(UUID.randomUUID(), "Box Car 1", "Houston", "FedEx"),
            Car(UUID.randomUUID(),"Box Car 2", "Chicago", "FedEx"),
            Car(UUID.randomUUID(),"Box Car 3", "Houston", "UPS"),
            Car(UUID.randomUUID(),"Box Car 4", "LA", "Old Dominion"),
            Car(UUID.randomUUID(),"Box Car 5", "LA", "FedEx"),
            Car(UUID.randomUUID(),"Box Car 6", "Houston", "Old Dominion")
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