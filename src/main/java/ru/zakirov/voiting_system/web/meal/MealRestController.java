package ru.zakirov.voiting_system.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.repository.MealRepository;
import ru.zakirov.voiting_system.repository.RestaurantRepository;

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

    @GetMapping("/meals/{id}")
    public ResponseEntity<Meal> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping(value = "/{restaurant_id}/meals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal, @PathVariable int restaurant_id) {
        log.info("create{} for restaurant {}", meal, restaurant_id);
        checkNew(meal);
        meal.setRestaurant(restaurantRepository.getById(restaurant_id));
        Meal created = repository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{restaurant_id}/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id, @PathVariable int restaurant_id) {
        log.info("update {} with id {} from restaurant {}", meal, id, restaurant_id);
        assureIdConsistent(meal, id);
        meal.setRestaurant(restaurantRepository.getById(restaurant_id));
        repository.save(meal);
    }

    @DeleteMapping("/meals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.delete(id);
    }
}
