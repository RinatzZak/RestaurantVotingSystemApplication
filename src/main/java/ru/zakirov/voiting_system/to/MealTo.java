package ru.zakirov.voiting_system.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MealTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 120)
    String description;

    @NotNull
    @Range(min = 1, max = 300000)
    BigDecimal price;

    @NotNull
    Date dateAdded;

    public MealTo(Integer id, String description, BigDecimal price, Date dateAdded) {
        super(id);
        this.description = description;
        this.price = price;
        this.dateAdded = dateAdded;
    }


}
