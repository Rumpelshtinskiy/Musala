package com.example.demo.controller.storage;

import com.example.demo.controller.medication.dto.MedicationDto;

public record StorageDto(
    MedicationDto medication,
    Integer count)
{}
