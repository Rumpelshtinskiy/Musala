package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Storage implements Serializable {

    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="drone_id")
    Drone drone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_name", nullable = false)
    Medication medication;

    Integer count;

    public Integer getWeight() {
        return Objects.nonNull(medication) ? medication.getWeight() * this.count : 0;
    }
}
