package com.example.demo.service;

import com.example.demo.model.Medication;
import com.example.demo.repository.MedicationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {
    @Mock
    private MedicationRepository medicationRepository;
    @InjectMocks
    private MedicationService medicationService;

    @Test
    @DisplayName("registerMedication should call repository.save")
    void registerMedication_shouldCallRepositorySave() {
        //Init
        Medication medication = new Medication();
        //Act
        medicationService.registerMedication(medication);
        //Assert
        verify(medicationRepository).save(medication);
    }
}
