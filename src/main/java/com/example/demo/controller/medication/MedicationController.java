package com.example.demo.controller.medication;

import com.example.demo.controller.medication.dto.MedicationDto;
import com.example.demo.controller.medication.dto.MedicationSaveDto;
import com.example.demo.mapper.MedicationMapper;
import com.example.demo.model.Medication;
import com.example.demo.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Validated
@RestController
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;
    @PostMapping("/medication/register")
    public ResponseEntity<MedicationDto> registerMedication(@Valid @RequestBody MedicationSaveDto medicationSaveDto) {
        Medication medication = MedicationMapper.INSTANCE.medicationDtoToMedication(medicationSaveDto);
        Medication savedMedication = medicationService.registerMedication(medication);
        return ResponseEntity.ok(MedicationMapper.INSTANCE.medicationToMedicationDto(savedMedication));
    }

    @PostMapping("/medication/{id}/image/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("id") String name, @RequestParam("image") MultipartFile file) throws IOException {
        medicationService.uploadImage(name, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/medication/{id}/image/download")
    public ResponseEntity<?> downloadImage(@PathVariable("id") String name){
        byte[] image = medicationService.getImage(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
}
