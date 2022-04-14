package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "meal")
@Getter
@Setter
public class Meal extends BaseEntity {

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 300000)
    private BigDecimal price;

    @Column(name = "added", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dateAdded = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Meal() {}

    public Meal(String description, BigDecimal price, Date dateAdded) {
        this(null, description, price, dateAdded);
    }

    public Meal(Integer id, String description, BigDecimal price, Date dateAdded) {
        super(id);
        this.description = description;
        this.price = price;
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "description = " + description + ", " +
                "price = " + price + ", " +
                "dateAdded = " + dateAdded + ")";
    }
}
