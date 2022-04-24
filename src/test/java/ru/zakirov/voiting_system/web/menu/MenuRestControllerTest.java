package ru.zakirov.voiting_system.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zakirov.voiting_system.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zakirov.voiting_system.web.menu.MenuTestData.MENU_MATCHER;
import static ru.zakirov.voiting_system.web.menu.MenuTestData.menu1;
import static ru.zakirov.voiting_system.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.zakirov.voiting_system.web.user.UserTestData.USER_MAIL;

class MenuRestControllerTest extends AbstractControllerTest {

    @Test
    void delete() {
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/profile/restaurants/" + RESTAURANT1_ID + "/menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    void getAll() {
    }

    @Test
    void createWithLocation() {
    }

    @Test
    void update() {
    }

    @Test
    void addingMealToMenu() {
    }

    @Test
    void removingMealFromMenu() {
    }
}