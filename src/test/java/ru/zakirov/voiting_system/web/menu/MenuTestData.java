package ru.zakirov.voiting_system.web.menu;

import org.assertj.core.util.DateUtil;
import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.model.Menu;
import ru.zakirov.voiting_system.web.MatcherFactory;

import java.util.List;

import static ru.zakirov.voiting_system.web.meal.MealTestData.*;
import static ru.zakirov.voiting_system.web.restaurant.RestaurantTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static final int MENU1_ID = 1;

    public static Menu menu1 = new Menu(MENU1_ID, DateUtil.now(), restaurant1, List.of(meal1, meal2, meal5));
    public static Menu menu2 = new Menu(MENU1_ID + 1, DateUtil.now(), restaurant3, List.of(meal6, meal8, meal9));
    public static Menu menu3 = new Menu(MENU1_ID + 2, DateUtil.now(), restaurant2, List.of(meal1, meal3, meal6));



    public static final List<Meal> meals1 = List.of(meal1, meal2, meal5);
    public static final List<Meal> meals2 = List.of(meal6, meal8, meal9);
    public static final List<Meal> meals3 = List.of(meal1, meal3, meal6);

}
