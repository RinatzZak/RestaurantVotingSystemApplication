package ru.zakirov.voiting_system.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.repository.MealRepository;
import ru.zakirov.voiting_system.repository.RestaurantRepository;
import ru.zakirov.voiting_system.to.MealTo;
import ru.zakirov.voiting_system.util.validation.MealUtil;

import javax.validation.Valid;
import java.net.URI;

import static ru.zakirov.voiting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voiting_system.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@RequestMapping(value = MealRestController.REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {
    static final String REST_URL = "/api/admin/restaurants";

    protected final MealRepository repository;
    protected final RestaurantRepository restaurantRepository;

    public MealRestController(MealRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{restaurant_id}/meals/{id}")
    public ResponseEntity<Meal> get(@PathVariable int restaurant_id, @PathVariable int id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping(value = "/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody MealTo mealTo, @PathVariable int restaurant_id) {
        log.info("create{} for restaurant {}", mealTo, restaurant_id);
        checkNew(mealTo);
        Meal created = repository.save(MealUtil.createNewFromTo(mealTo));
        created.setRestaurant(restaurantRepository.getById(restaurant_id));
        repository.save(created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurant_id}/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody MealTo mealTo, @PathVariable int id, @PathVariable int restaurant_id) {
        log.info("update {} with id {} from restaurant {}", mealTo, id, restaurant_id);
        assureIdConsistent(mealTo, id);
        Meal meal = repository.getById(id);
        meal.setRestaurant(restaurantRepository.getById(restaurant_id));
        repository.save(MealUtil.updateFromTo(meal, mealTo));
    }

    @DeleteMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }
}
