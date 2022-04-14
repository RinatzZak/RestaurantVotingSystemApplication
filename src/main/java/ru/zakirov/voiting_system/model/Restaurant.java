package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
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
public class Restaurant extends BaseEntity {

    @Column(name = "description", unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String description;

    @Column(name = "address")
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
    @Column(name = "menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Meal> menu;

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