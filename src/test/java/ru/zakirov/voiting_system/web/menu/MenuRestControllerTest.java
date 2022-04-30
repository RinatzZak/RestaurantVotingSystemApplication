package ru.zakirov.voiting_system.web.menu;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zakirov.voiting_system.model.Menu;
import ru.zakirov.voiting_system.repository.MenuRepository;
import ru.zakirov.voiting_system.util.JsonUtil;
import ru.zakirov.voiting_system.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zakirov.voiting_system.web.meal.MealTestData.meal5;
import static ru.zakirov.voiting_system.web.meal.MealTestData.meal8;
import static ru.zakirov.voiting_system.web.menu.MenuTestData.*;
import static ru.zakirov.voiting_system.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.zakirov.voiting_system.web.user.UserTestData.ADMIN_MAIL;
import static ru.zakirov.voiting_system.web.user.UserTestData.USER_MAIL;

class MenuRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/api/admin/restaurants/1/menu/" + MENU1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals(menuRepository.findAll(), menuList2);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/restaurants/" + RESTAURANT1_ID + "/menu"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/admin/menus"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menuList));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post("/api/admin/restaurants/4/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isCreated());

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.getById(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDoubletMenu() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        perform(MockMvcRequestBuilders.post("/api/admin/restaurants/1/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        try {
            MockedStatic<LocalDate> mocked = Mockito.mockStatic(LocalDate.class);
            mocked.when(LocalDate::now).thenReturn(LocalDate.of(2030, 3, 11));
            Menu updated = MenuTestData.getUpdated();
            perform(MockMvcRequestBuilders.put("/api/admin/restaurants/1/menu/" + MENU1_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isNoContent());

            MENU_MATCHER.assertMatch(menuRepository.getById(MENU1_ID), updated);
        } catch (Exception ignored) {
        }
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void addingMealToMenu() throws Exception {
        perform(MockMvcRequestBuilders.post("/api/admin/restaurants/1/menu/meals/" + meal8.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menu1)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void removingMealFromMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete("/api/admin/restaurants/1/menu/meals/" + meal5.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menu1)))
                .andExpect(status().isNoContent());
    }
}