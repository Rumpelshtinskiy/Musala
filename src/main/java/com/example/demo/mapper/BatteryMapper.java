package com.example.demo.mapper;

import com.example.demo.controller.drone.dto.BatteryDto;
import org.mapstruct.Mapper;

@Mapper
public interface BatteryMapper {
    BatteryMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(BatteryMapper.class);
    default BatteryDto getBatteryCapacity(Byte batteryLevel) {
        if(batteryLevel == null) {
            return null;
        }
        BatteryDto batteryDto = new BatteryDto();
        batteryDto.setBatteryLevel(batteryLevel);
        return batteryDto;
    }
}
