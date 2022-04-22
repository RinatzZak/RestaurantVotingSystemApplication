package ru.zakirov.voiting_system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class Restaurant extends BaseEntity {

    @Column(name = "description", unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String description;

    @Column(name = "address")
    @NotBlank
    private String address;

    public Restaurant() {
    }

    public Restaurant(String description, String address) {
        this(null, description, address);
    }

    public Restaurant(Integer id, String description, String address) {
        super(id);
        this.description = description;
        this.address = address;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "description = " + description + ", " +
                "address = " + address + ")";
    }
}