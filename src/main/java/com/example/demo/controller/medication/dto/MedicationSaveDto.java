package com.example.demo.controller.medication.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record MedicationSaveDto (
        @NotNull(message = "Name cannot be null")
        @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "For name allowed only letters, numbers, ‘-‘, ‘_’")
        String name,
        @NotNull(message = "Weight cannot be null")
        Integer weight,
        @NotNull(message = "Code cannot be null")
        @Pattern(regexp = "^[A-Z0-9_]+$", message = "For code allowed only upper case letters, underscore and numbers")
        String code
) {}
