package com.example.demo.mapper;

import com.example.demo.controller.medication.dto.MedicationDto;
import com.example.demo.controller.medication.dto.MedicationSaveDto;
import com.example.demo.model.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface MedicationMapper {

    MedicationMapper INSTANCE = Mappers.getMapper(MedicationMapper.class);
    MedicationDto medicationToMedicationDto(Medication medication);

    Medication medicationDtoToMedication(MedicationSaveDto dto);
    Collection<MedicationDto> medicationsToMedicationsDto(Collection<Medication> medication);
}
