package com.example.demo.service;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.model.State;
import com.example.demo.model.Storage;
import com.example.demo.repository.DroneRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {

    private static final UUID ID = UUID.randomUUID();
    @Mock
    DroneRepository droneRepository;
    @Mock
    DroneValidator droneValidator;
    @Mock
    MedicationService medicationService;
    @InjectMocks
    DroneService droneService;

    @Test
    @DisplayName("Should invoke save drone repository method with given drone")
    void testRegisterDrone() {
        //Init
        Drone drone = new Drone();
        //Act
        droneService.registerDrone(drone);
        //Assert
        verify(droneRepository).save(drone);
    }

    @Test
    @DisplayName("Should return drone with loading state")
    void testLoadingDrone() {
        //Init
        Drone drone = new Drone();
        drone.setId(ID);
        when(droneRepository.findById(ID)).thenReturn(Optional.of(drone));
        Medication medication = new Medication();
        medication.setName("med1");
        Storage storage1 = new Storage();
        storage1.setMedication(medication);

        medication = new Medication();
        medication.setName("med2");
        Storage storage2 = new Storage();
        storage2.setMedication(medication);
        List<Storage> list = List.of(storage1, storage2);
        //Act
        droneService.loadingDrone(ID, list);
        //Assert
        Assertions.assertThat(drone.getState()).isEqualTo(State.LOADING);
        Assertions.assertThat(drone.getStorages().containsAll(list)).isTrue();
        verify(droneValidator).validateDroneMedications(drone, list);
        verify(droneValidator).validateDroneBattery(drone);
    }

    @Test
    @DisplayName("Should throw exception when drone not found")
    void testLoadingDrone2() {
        //Init
        when(droneRepository.findById(ID)).thenReturn(Optional.empty());
        //Act
        //Assert
        Assertions.assertThatThrownBy(() -> droneService.loadingDrone(ID, Collections.EMPTY_LIST))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("The drone: %s does not exist.", ID));
    }

    @Test
    @DisplayName("Should return medications stored in drone")
    void testGetMedications() {
        //Init
        Drone drone = new Drone();
        List<Storage> list = List.of(new Storage(), new Storage());
        drone.setId(ID);
        drone.setStorages(list);
        when(droneRepository.findById(ID)).thenReturn(Optional.of(drone));
        //Act
        Collection<Storage> result = droneService.getMedications(drone);
        //Assert
        Assertions.assertThat(result).isEqualTo(list);
    }

    @Test
    @DisplayName("Should throw exception when drone not found")
    void testGetMedications2() {
        //Init
        Drone drone = new Drone();
        drone.setId(ID);
        when(droneRepository.findById(ID)).thenReturn(Optional.empty());
        //Act
        //Assert
        Assertions.assertThatThrownBy(() -> droneService.getMedications(drone))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("The drone: %s does not exist.", ID));
    }

    @Test
    @DisplayName("Should invoke findAllByState method with given State.LOADING")
    void testGetAvailableDrones() {
        //Init
        //Act
        droneService.getAvailableDrones();
        //Assert
        verify(droneRepository).findByStateIn(List.of(State.LOADING, State.IDLE));
    }

    @Test
    @DisplayName("Should return battery capacity of drone")
    void testGetDroneBatteryCapacity() {
        //Init
        Drone drone = new Drone();
        drone.setId(ID);
        Byte expectedBatteryCapacity = 100;
        drone.setBatteryCapacity(expectedBatteryCapacity);
        when(droneRepository.findById(ID)).thenReturn(Optional.of(drone));

        //Act
        Byte result = droneService.getDroneBatteryCapacity(drone);
        //Assert
        Assertions.assertThat(result).isEqualTo(expectedBatteryCapacity);
    }

    @Test
    @DisplayName("Should throw exception when drone not found")
    void testGetDroneBatteryCapacity2() {
        //Init
        Drone drone = new Drone();
        drone.setId(ID);
        when(droneRepository.findById(ID)).thenReturn(Optional.empty());
        //Act
        //Assert
        Assertions.assertThatThrownBy(() -> droneService.getDroneBatteryCapacity(drone))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("The drone: %s does not exist.", ID));
    }
}
