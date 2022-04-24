package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu extends BaseEntity {

    @Column(name = "date_added", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dateAdded = new Date();

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

    public Menu(Integer id, Date dateAdded) {
        super(id);
        this.dateAdded = dateAdded;
    }

    public Menu() {
    }

    public Menu(Integer id, Date dateAdded, Restaurant restaurant, List<Meal> meals) {
        super(id);
        this.dateAdded = dateAdded;
        this.restaurant = restaurant;
        setMeals(meals);
    }
}
