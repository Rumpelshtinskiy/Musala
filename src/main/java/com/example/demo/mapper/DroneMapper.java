package com.example.demo.mapper;

import com.example.demo.controller.drone.dto.DroneDto;
import com.example.demo.controller.drone.dto.DroneSaveDto;
import com.example.demo.model.Drone;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(uses = {StorageMapper.class})
public interface DroneMapper {
    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

    DroneDto droneToDroneDto(Drone drone);
    Drone droneDtoToDrone(DroneSaveDto drone);
    Collection<DroneDto> dronesToDronesDto(Collection<Drone> drone);
}
