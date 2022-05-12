package ru.zakirov.voting_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Restaurant extends NamedEntity {
    @Column(name = "address")
    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.address);
    }

    public Restaurant(String name, String address) {
        this(null, name, address);
    }

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}