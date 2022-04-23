package ru.zakirov.voiting_system.web.meal;

import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.web.MatcherFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class);

    public static final int MEAL1_ID = 1;
    public static final int NOT_FOUND = 100;

    public static final Meal meal1 = new Meal(MEAL1_ID, "Rice with seafood", new BigDecimal("400.00"));
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, "Soup puree", new BigDecimal("300.00"));
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, "Royal roast beef", new BigDecimal("600.00"));
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, "Orange juice", new BigDecimal("10.00"));
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, "Coffee", new BigDecimal("10.00"));
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, "Milk cocktail", new BigDecimal("20.00"));
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, "Orange ice cream", new BigDecimal("50.00"));
    public static final Meal meal8 = new Meal(MEAL1_ID + 7, "Cherry ice cream", new BigDecimal("55.00"));
    public static final Meal meal9 = new Meal(MEAL1_ID + 8, "Apple ice cream", new BigDecimal("51.00"));

    public static final List<Meal> meals = Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8, meal9);

    public static Meal getNew() {
        return new Meal(null, "NewMeal", new BigDecimal(777));
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "UpdatedMeal", new BigDecimal(111));
    }
}
