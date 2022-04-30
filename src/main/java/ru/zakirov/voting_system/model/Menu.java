package ru.zakirov.voting_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu extends BaseEntity {

    @Column(name = "date_added", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateAdded = LocalDate.now();

    @OneToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
            (
                    name = "menu_meal",
                    joinColumns = @JoinColumn(name = "menu_id", nullable = false),
                    inverseJoinColumns = @JoinColumn(name = "meals_id")
            )
    private List<Meal> meals = new ArrayList<>();

    public Menu(Integer id, LocalDate dateAdded) {
        super(id);
        this.dateAdded = dateAdded;
    }

    public Menu() {
    }

    public Menu(Integer id, LocalDate dateAdded, Restaurant restaurant, List<Meal> meals) {
        super(id);
        this.dateAdded = dateAdded;
        this.restaurant = restaurant;
        setMeals(meals);
    }
}
