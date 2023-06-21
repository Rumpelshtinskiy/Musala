package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
public class Drone implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(value = STRING)
    @NotNull(message = "Model cannot be null")
    private Model model;

    @NotNull(message = "Weight limit cannot be null")
    @Max(value = 500, message = "Weight limit cannot be more than 500 gr")
    @Min(value = 0, message = "Weight limit cannot be less than 0 gr")
    private Integer weightLimit;

    @NotNull(message = "Battery capacity cannot be null")
    @Max(value = 100, message = "Battery capacity cannot be more than 100 %")
    @Min(value = 0, message = "Battery capacity cannot be less than 0 %")
    private Byte batteryCapacity = 100;

    @Enumerated(value = STRING)
    @NotNull(message = "State cannot be null")
    private State state = State.IDLE;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<Storage> storages = new ArrayList<>();

    public void addStorages(Collection<Storage> storages) {
        Map<String, Storage> map = storages.stream().collect(Collectors.toMap(s -> s.getMedication().getName(), s -> s));
        for(Storage storage : this.storages) {
            Storage st = map.get(storage.getMedication().getName());
            if(Objects.nonNull(st)) {
                storage.setCount(storage.getCount() + st.getCount());
            }
            map.remove(storage.getMedication().getName());
        }
        this.storages.addAll(map.values());
    }
}
