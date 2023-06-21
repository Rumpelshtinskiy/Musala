package com.example.demo.mapper;

import com.example.demo.controller.storage.StorageDto;
import com.example.demo.controller.storage.StorageLoadingDto;
import com.example.demo.model.Medication;
import com.example.demo.model.Storage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mapper(uses = {MedicationMapper.class})
public interface StorageMapper {
    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    StorageDto medicationsToMedicationsDto(Storage medication);
    Collection<StorageDto> medicationsToMedicationsDto(Collection<Storage> medication);
    default Collection<Storage> medicationsToStorages(Collection<Medication> medications) {
        Map<String, Storage> map = new HashMap<>();
        for (Medication medication : medications) {
            Storage st = map.get(medication.getName());
            if(Objects.isNull(st)){
                Storage storage = new Storage();
                storage.setMedication(medication);
                storage.setCount(1);
                map.put(medication.getName(), storage);
            }
            else {
                st.setCount(st.getCount() + 1);
            }
        }
        return map.values();
    }

    default Collection<Storage> storageLoadingDtoToStorage(Collection<StorageLoadingDto> dto) {
        return dto.stream().map(loadingDto -> {
                    Medication medication = new Medication();
                    medication.setName(loadingDto.name());
                    Storage storage = new Storage();
                    storage.setMedication(medication);
                    storage.setCount(loadingDto.count());
                    return storage;
                })
                .toList();
    }
}
