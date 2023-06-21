package com.example.demo.controller.drone;

import com.example.demo.controller.drone.dto.BatteryDto;
import com.example.demo.controller.drone.dto.DroneDto;
import com.example.demo.controller.drone.dto.DroneSaveDto;
import com.example.demo.controller.storage.StorageDto;
import com.example.demo.controller.storage.StorageLoadingDto;
import com.example.demo.mapper.BatteryMapper;
import com.example.demo.mapper.DroneMapper;
import com.example.demo.mapper.StorageMapper;
import com.example.demo.model.Drone;
import com.example.demo.model.Storage;
import com.example.demo.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DroneController {
    private final DroneService droneService;

    @PostMapping("/drone/register")
    public ResponseEntity<DroneDto> registerDrone(@Valid @RequestBody DroneSaveDto droneSaveDto) {
        Drone drone = DroneMapper.INSTANCE.droneDtoToDrone(droneSaveDto);
        Drone savedDrone = droneService.registerDrone(drone);
        return ResponseEntity.ok(DroneMapper.INSTANCE.droneToDroneDto(savedDrone));
    }

    @PostMapping("/drone/{id}/loading")
    public ResponseEntity<DroneDto> loadingDrone(@PathVariable("id") UUID id, @RequestBody Collection<StorageLoadingDto> medicationsSaveDto) {
        Collection<Storage> medications = StorageMapper.INSTANCE.storageLoadingDtoToStorage(medicationsSaveDto);
        Drone savedDrone = droneService.loadingDrone(id, medications);
        return ResponseEntity.ok(DroneMapper.INSTANCE.droneToDroneDto(savedDrone));
    }

    @GetMapping("/drone/{id}/loaded/medication")
    public ResponseEntity<Collection<StorageDto>> getLoadedMedication(@PathVariable("id") UUID id) {
        Drone drone = droneService.getDrone(id);
        return ResponseEntity.ok(StorageMapper.INSTANCE.medicationsToMedicationsDto(drone.getStorages()));
    }

    @GetMapping("/drone/available")
    public ResponseEntity<Collection<DroneDto>> getAvailableDrones() {
        Collection<Drone> drones = droneService.getAvailableDrones();
        return ResponseEntity.ok(DroneMapper.INSTANCE.dronesToDronesDto(drones));
    }

    @GetMapping("/drone/{id}/battery")
    public ResponseEntity<BatteryDto> getBatteryCapacity(@PathVariable("id") UUID id) {
        Drone drone = droneService.getDrone(id);
        return ResponseEntity.ok(BatteryMapper.INSTANCE.getBatteryCapacity(drone.getBatteryCapacity()));
    }
}
