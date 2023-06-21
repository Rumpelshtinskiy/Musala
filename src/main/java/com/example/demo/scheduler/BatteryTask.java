package com.example.demo.scheduler;

import com.example.demo.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatteryTask {

    private final DroneService droneService;

    @Scheduled(fixedRate = 25, timeUnit = TimeUnit.SECONDS)
    public void reportBatteryCapacity() {
        droneService.getDrones()
                .stream()
                .forEach(drone -> log.info(
                        "The drone: {} has {} % battery capacity left",
                        drone.getId(),
                        drone.getBatteryCapacity())
                );
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void dronePowerConsuming() {
        droneService.decreaseBatteryCapacity();
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void dronePowerCharging() {
        droneService.increaseBatteryCapacity();
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void droneStateChanging() {
        droneService.updateDronesState();
    }
}
