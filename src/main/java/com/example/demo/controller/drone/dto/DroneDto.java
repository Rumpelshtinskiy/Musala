package com.example.demo.controller.drone.dto;

import com.example.demo.controller.storage.StorageDto;
import com.example.demo.model.Medication;
import com.example.demo.model.Model;
import com.example.demo.model.State;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;

public record DroneDto (

        UUID id,

        @NotNull(message = "Model cannot be null")
        Model model,

        @NotNull(message = "Weight limit cannot be null")
        @Max(value = 500, message = "Weight limit cannot be more than 500 gr")
        @Min(value = 0, message = "Weight limit cannot be less than 0 gr")
        Integer weightLimit,

        @NotNull(message = "Battery capacity cannot be null")
        @Max(value = 100, message = "Battery capacity cannot be more than 100 %")
        @Min(value = 0, message = "Battery capacity cannot be less than 0 %")
        Byte batteryCapacity,

        @NotNull(message = "State cannot be null")
        State state,

        Collection<StorageDto> storages
) {
}
