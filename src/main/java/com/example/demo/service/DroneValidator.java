package com.example.demo.service;

import com.example.demo.model.Drone;
import com.example.demo.model.Storage;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class DroneValidator {
    static final Byte MIN_BATTERY_CAPACITY = 25;
    public void validateDroneMedications(Drone drone, Collection<Storage> storages) {
        Integer droneWeight = drone.getStorages().stream()
                .map(Storage::getWeight)
                .reduce(0, Integer::sum);

        Integer weight = storages.stream()
                .map(Storage::getWeight)
                .reduce(droneWeight, Integer::sum);

        if (weight > drone.getWeightLimit()) {
            throw new IllegalArgumentException("Medication weight is more than drone weight limit: " + drone.getWeightLimit() + " gr");
        }
    }

    public void validateDroneBattery(Drone drone) {

        if(drone.getBatteryCapacity() < MIN_BATTERY_CAPACITY) {
            throw new IllegalArgumentException("Drone battery capacity is less than minimum: " + MIN_BATTERY_CAPACITY + " %");
        }
    }

    public void validateMedications(Collection<Storage> storages) {
        List<String> list = storages.stream()
                .filter(st -> Objects.isNull(st.getMedication().getCode()))
                .map(st -> st.getMedication().getName())
                .toList();

        if(!list.isEmpty()) {
            throw new IllegalArgumentException("The next medications are not registered: "
                    + String.join(", ", list));
        }
    }
}
