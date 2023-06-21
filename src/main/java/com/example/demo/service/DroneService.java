package com.example.demo.service;

import com.example.demo.model.Drone;
import com.example.demo.model.Medication;
import com.example.demo.model.State;
import com.example.demo.model.Storage;
import com.example.demo.repository.DroneRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DroneService {
    private final DroneRepository repository;
    private final DroneValidator validator;
    private final MedicationService medicationService;
    private final DroneService self;

    public DroneService(
            DroneRepository repository,
            DroneValidator validator,
            @Lazy DroneService self,
            MedicationService medicationService)
    {
        this.repository = repository;
        this.validator = validator;
        this.self = self;
        this.medicationService = medicationService;
    }

    public Drone registerDrone(Drone drone) {
        return repository.save(drone);
    }

    @Transactional
    public Drone loadingDrone(UUID droneId, Collection<Storage> storages) {
        Drone drone = repository.findById(droneId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("The drone: %s does not exist.", droneId)));

        Collection<Medication> meds = medicationService.getMedications(storages.stream()
                .map(Storage::getMedication)
                .collect(Collectors.toList()));
        Map<String, Medication> medMap = meds.stream().collect(Collectors.toMap(Medication::getName, m -> m));

        storages.stream().forEach(storage -> {
            storage.setDrone(drone);
            Medication med = medMap.get(storage.getMedication().getName());
            if(Objects.nonNull(med)) {
                storage.setMedication(med);
            }
        });

        validator.validateMedications(storages);
        validator.validateDroneMedications(drone, storages);
        validator.validateDroneBattery(drone);

        drone.addStorages(storages);
        drone.setState(State.LOADING);

        return drone;
    }

    void updateStorages(Drone drone, Collection<Storage> storages) {
        Map<String, Integer> map = storages.stream().collect(Collectors.toMap(s -> s.getMedication().getName(), s -> s.getCount()));
        for(Storage storage : drone.getStorages()) {
            Integer count = map.getOrDefault(storage.getMedication().getName(), 0);
            storage.setCount(storage.getCount() + count);
        }
    }

    @Transactional
    public Collection<Storage> getMedications(Drone drone) {
        Drone dr = repository.findById(drone.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("The drone: %s does not exist.", drone.getId())));
        return dr.getStorages();
    }


    public Collection<Drone> getAvailableDrones() {
        return repository.findByStateIn(List.of(State.LOADING, State.IDLE));
    }

    public Byte getDroneBatteryCapacity(Drone drone) {
        Drone dr = repository.findById(drone.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("The drone: %s does not exist.", drone.getId())));
        return dr.getBatteryCapacity();
    }

    public Drone getDrone(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("The drone: %s does not exist.", id)));
    }

    public Collection<Drone> getDrones() {
        return repository.findAll();
    }

    @Transactional
    public Collection<Drone> getDronesInWork() {
        return repository.findByStateIn(List.of(State.RETURNING, State.DELIVERING));
    }

    public Collection<Drone> getReturnedDrones() {
        return repository.findByStateIn(List.of(State.DELIVERED));
    }

    public Collection<Drone> getOnIdleDrones() {
        return repository.findByStateIn(List.of(State.IDLE));
    }

    @Transactional
    public void decreaseBatteryCapacity() {
        self.getDronesInWork()
                .stream()
                .forEach(drone -> {
                    drone.setBatteryCapacity((byte) (drone.getBatteryCapacity() - 5));
                    if (drone.getBatteryCapacity() == 25) {
                        drone.setState(State.RETURNING);
                    }
                });
    }

    @Transactional
    public void increaseBatteryCapacity() {
        self.getOnIdleDrones()
                .stream()
                .filter(drone -> drone.getBatteryCapacity() < 100)
                .forEach(drone -> {
                    drone.setBatteryCapacity((byte) (drone.getBatteryCapacity() + 1));
                });
    }

    @Transactional
    public void updateDronesState() {
        self.getReturnedDrones()
                .stream()
                .forEach(drone -> drone.setState(State.IDLE));
    }
}
