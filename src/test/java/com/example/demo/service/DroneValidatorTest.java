package com.example.demo.service;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.model.Storage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DroneValidatorTest {
    @InjectMocks
    private DroneValidator droneValidator;

    @Test
    @DisplayName("Should no throw IllegalArgumentException")
    void testValidateDroneMedications() {
        // Init
        Drone drone = new Drone();
        drone.setWeightLimit(400);
        Medication medication = new Medication();
        medication.setWeight(200);
        Storage storage1 = new Storage();
        storage1.setMedication(medication);
        storage1.setCount(1);

        Medication medication2 = new Medication();
        medication2.setWeight(200);
        Storage storage2 = new Storage();
        storage2.setMedication(medication2);
        storage2.setCount(1);
        // Act and Assert
        droneValidator.validateDroneMedications(drone, List.of(storage1, storage2));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException")
    void testValidateDroneMedications2() {
        // Init
        Drone drone = new Drone();
        drone.setWeightLimit(400);
        Medication medication = new Medication();
        medication.setWeight(200);
        Storage storage1 = new Storage();
        storage1.setMedication(medication);
        storage1.setCount(1);

        Medication medication2 = new Medication();
        medication2.setWeight(201);
        Storage storage2 = new Storage();
        storage2.setMedication(medication2);
        storage2.setCount(1);
        // Act and Assert
        Assertions.assertThatThrownBy(() -> droneValidator.validateDroneMedications(drone, List.of(storage1, storage2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Medication weight is more than drone weight limit: " + drone.getWeightLimit() + " gr");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException")
    void testValidateDroneBatteryCapacity() {
        // Init
        Drone drone = new Drone();
        drone.setBatteryCapacity((byte) 24);
        // Act and Assert
        Assertions.assertThatThrownBy(() -> droneValidator.validateDroneBattery(drone))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Drone battery capacity is less than minimum: " + DroneValidator.MIN_BATTERY_CAPACITY + " %");
    }

    @Test
    @DisplayName("Should no throw IllegalArgumentException")
    void testValidateDroneBatteryCapacity2() {
        // Init
        Drone drone = new Drone();
        drone.setBatteryCapacity((byte) 25);
        // Act and Assert
        droneValidator.validateDroneBattery(drone);
    }
}
