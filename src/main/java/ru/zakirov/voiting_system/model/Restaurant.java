package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "description", unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String description;

    @Column(name = "address")
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", orphanRemoval = true)
    @Column(name = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Meal> menu;

    public Restaurant(String description, List<Meal> menu, String address) {
        this.description = description;
        this.menu = menu;
        this.address = address;
    }

    public Restaurant(Integer id, String description, List<Meal> menu, String address) {
        super(id);
        this.description = description;
        this.menu = menu;
        this.address = address;
    }
}