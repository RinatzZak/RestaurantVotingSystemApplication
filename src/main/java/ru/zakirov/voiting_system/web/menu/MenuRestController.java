package ru.zakirov.voiting_system.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voiting_system.model.Menu;
import ru.zakirov.voiting_system.repository.MealRepository;
import ru.zakirov.voiting_system.repository.MenuRepository;
import ru.zakirov.voiting_system.repository.RestaurantRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.zakirov.voiting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voiting_system.util.validation.ValidationUtil.checkNewMenu;

@RestController
@Slf4j
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "menus")
public class MenuRestController {

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    private final MealRepository mealRepository;

    public MenuRestController(MenuRepository menuRepository, RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

    @DeleteMapping("/api/admin/restaurants/{restaurant_id}/menu/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurant_id, @PathVariable int id) {
        log.info("delete menu for restaurant{}", restaurant_id);
        Menu menu = menuRepository.getById(id);
        menu.setRestaurant(null);
        menuRepository.deleteExisted(id);
    }

    @GetMapping("/api/restaurants/{restaurant_id}/menu")
    @CacheEvict(allEntries = true)
    public Menu getForRestaurant(@PathVariable int restaurant_id) {
        return menuRepository.getByRestaurantId(restaurant_id);
    }

    @GetMapping("/api/admin/menus")
    public List<Menu> getAll() {
        log.info("getAll");
        return menuRepository.findAll();
    }

    @PostMapping("/api/admin/restaurants/{restaurant_id}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restaurant_id) {
        log.info("create{} for restaurant{}", menu, restaurant_id);
        checkNewMenu(menuRepository.getByRestaurantId(restaurant_id));
        // НАДО ПЕРЕДЕЛАТЬ! ошибка при создании нового меню.`
        menu.setRestaurant(restaurantRepository.getById(restaurant_id));
        menu.setMeals(menu.getMeals());
        Menu created = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{restaurant_id}/menu")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/api/admin/restaurants/{restaurant_id}/menu/{menu_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable int menu_id, @PathVariable int restaurant_id) {
        log.info("update menu for restaurant{}", restaurant_id);
        assureIdConsistent(menu, menu_id);
        menu.setDateAdded(menu.getDateAdded());
        menu.setMeals(menu.getMeals());
        menuRepository.save(menu);
    }

    @PostMapping(value = "/api/admin/restaurants/{restaurant_id}/menu/meals/{meals_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addingMealToMenu(@PathVariable int restaurant_id, @PathVariable int meals_id) {
        log.info("add meal{} to menu from restaurant{}", meals_id, restaurant_id);
        Menu menu = getForRestaurant(restaurant_id);
        menu.getMeals().add(mealRepository.getById(meals_id));
        menuRepository.save(menu);
    }

    @DeleteMapping("/api/admin/restaurants/{restaurant_id}/menu/meals/{meals_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void removingMealFromMenu(@PathVariable int restaurant_id, @PathVariable int meals_id) {
        log.info("delete meal{} from restaurant{} menu", meals_id, restaurant_id);
        Menu menu = getForRestaurant(restaurant_id);
        menu.getMeals().remove(mealRepository.getById(meals_id));
        menuRepository.save(menu);
    }
}
