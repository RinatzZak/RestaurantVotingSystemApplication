package ru.zakirov.voiting_system.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "restaurant", indexes = {
        @Index(name = "idx_restaurant_address", columnList = "address")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Restaurant extends NamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Meal> meals = new ArrayList<>();

    public Restaurant(Integer id, String name, String address, List<Meal> meals) {
        super(id, name);
        this.address = address;
        this.meals = meals;
    }
}
