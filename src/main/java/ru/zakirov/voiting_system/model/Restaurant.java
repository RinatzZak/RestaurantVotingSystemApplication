package ru.zakirov.voiting_system.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "restoraunt")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @Column(name = "address")
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    private List<Meal> menu;

    public Restaurant(List<Meal> menu, String address) {
        this.menu = menu;
        this.address = address;
    }

    public Restaurant(Integer id, String name, List<Meal> menu, String address) {
        super(id, name);
        this.menu = menu;
        this.address = address;
    }
}