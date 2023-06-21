package com.example.demo.controller.medication.dto;

public record MedicationDto(

        String name,
        Integer weight,
        String code,
        byte[] image
) {}
