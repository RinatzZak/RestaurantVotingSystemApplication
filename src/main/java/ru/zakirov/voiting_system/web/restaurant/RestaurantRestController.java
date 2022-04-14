package ru.zakirov.voiting_system.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voiting_system.model.Restaurant;
import ru.zakirov.voiting_system.repository.RestaurantRepository;
import ru.zakirov.voiting_system.to.RestaurantTo;
import ru.zakirov.voiting_system.util.RestaurantUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.zakirov.voiting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voiting_system.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    private final RestaurantRepository repository;

    public RestaurantRestController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @DeleteMapping("/api/admin/restaurants/{id}")
    @CacheEvict(value = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @GetMapping("/api/profile/restaurants")
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

   @GetMapping("/api/profile/restaurants{id}/menu")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int id) {
        log.info("get restaurant{} with menu", id);
        return ResponseEntity.of(repository.getWithMeals(id));
    }

    @PostMapping(value = "/api/admin/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create{}", restaurantTo);
        checkNew(restaurantTo);
        Restaurant created = repository.save(RestaurantUtil.createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/api/admin/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update restaurant with id{}", id);
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = repository.getById(id);
        repository.save(RestaurantUtil.updateFromTo(restaurant, restaurantTo));
    }
}
