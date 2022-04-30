package ru.zakirov.voting_system.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "meals")
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

    public Meal() {
    }

    public Meal(Meal m) {
        this(m.id, m.description, m.price);
    }

    public Meal(String description, BigDecimal price) {
        this(null, description, price);
    }

    public Meal(Integer id, String description, BigDecimal price) {
        super(id);
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "description = " + description + ", " +
                "price = " + price + ", " + ")";
    }
}
