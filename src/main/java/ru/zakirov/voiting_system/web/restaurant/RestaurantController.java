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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.zakirov.voiting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voiting_system.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "restaurants", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }

    @GetMapping
    @Cacheable("api/profile/restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create{}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update{} with id{}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }
}
