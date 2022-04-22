package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
            (
                    name = "menu_meal",
                    joinColumns = @JoinColumn(name = "menu_id", nullable = false),
                    inverseJoinColumns = @JoinColumn(name = "meal_id", nullable = false)
            )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Meal> meals;

    public Menu(Integer id, Date dateAdded) {
        super(id);
        this.dateAdded = dateAdded;
    }

    public Menu() {
    }

    public Menu(Integer id, Date dateAdded, Restaurant restaurant, Set<Meal> meals) {
        super(id);
        this.dateAdded = dateAdded;
        this.restaurant = restaurant;
        setMeals(meals);
    }

    public void setMeals(Collection<Meal> meals) {
        this.meals = CollectionUtils.isEmpty(meals) ? new ArrayList<>() : List.copyOf(meals);
    }
}
