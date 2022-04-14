package ru.zakirov.voiting_system.util.validation;

import lombok.experimental.UtilityClass;
import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.to.MealTo;

@UtilityClass
public class MealUtil {

    public static Meal createNewFromTo(MealTo mealTo) {
        return new Meal(null, mealTo.getDescription(), mealTo.getPrice(), mealTo.getDateAdded());
    }

    public static Meal updateFromTo(Meal meal, MealTo mealTo) {
        meal.setDescription(mealTo.getDescription());
        meal.setPrice(mealTo.getPrice());
        meal.setDateAdded(mealTo.getDateAdded());
        return meal;
    }

//    public static MealTo transformToTo(Meal meal) {
//        return new MealTo(meal);
//    }
}
