package com.example.demo.service;

import com.example.demo.model.Medication;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository repository;

    public Medication registerMedication(Medication medication) {
        return repository.save(medication);
    }

    public Collection<Medication> getMedications(Collection<Medication> medications) {
        return repository.findAllById(medications.stream().map(Medication::getName).toList());
    }

    public void uploadImage(String name, MultipartFile file) throws IOException {
        Medication medication = repository.findById(name)
                .orElseThrow(() -> new RuntimeException(String.format("Medication: %s not found", name)));
        medication.setImage(ImageUtil.compressImage(file.getBytes()));
        repository.save(medication);
    }

    @Transactional
    public byte[] getImage(String name) {
        Medication medication = repository.findById(name)
                .orElseThrow(() -> new RuntimeException(String.format("Medication: %s not found", name)));
        return ImageUtil.decompressImage(medication.getImage());
    }
}
