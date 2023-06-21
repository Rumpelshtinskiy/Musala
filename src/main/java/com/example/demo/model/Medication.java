package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class Medication {
    @Id
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "For name allowed only letters, numbers, ‘-‘, ‘_’")
    String name;

    @NotNull(message = "Weight cannot be null")
    Integer weight;

    @NotNull(message = "Code cannot be null")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "For code allowed only upper case letters, underscore and numbers")
    String code;

    @Lob
    @Column()
    private byte[] image;

}
