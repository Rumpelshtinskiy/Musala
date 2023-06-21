package com.example.demo.controller.drone.dto;

import com.example.demo.model.Model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public record DroneSaveDto (
        @NotNull(message = "Model cannot be null")
        Model model,

        @NotNull(message = "Weight limit cannot be null")
        @Max(value = 500, message = "Weight limit cannot be more than 500 gr")
        @Min(value = 0, message = "Weight limit cannot be less than 0 gr")
        Integer weightLimit
){
}
