package ru.zakirov.voiting_system.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voiting_system.model.Menu;
import ru.zakirov.voiting_system.model.Restaurant;
import ru.zakirov.voiting_system.repository.MenuRepository;
import ru.zakirov.voiting_system.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static ru.zakirov.voiting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voiting_system.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    private final RestaurantRepository repository;

    private final MenuRepository menuRepository;

    public RestaurantRestController(RestaurantRepository repository, MenuRepository menuRepository) {
        this.repository = repository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/api/restaurants/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get{}", id);
        return ResponseEntity.of(Objects.requireNonNull(repository.findById(id)));
    }

    @DeleteMapping("/api/admin/restaurants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        Menu menu = menuRepository.getByRestaurantId(id);
        if (menu != null) {
            menuRepository.delete(menuRepository.getByRestaurantId(id));
            menuRepository.flush();
        }
        repository.deleteExisted(id);
    }

    @GetMapping("/api/restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @PostMapping(value = "/api/admin/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create{}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/api/admin/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update restaurant with id{}", id);
        assureIdConsistent(restaurant, id);
        restaurant.setDescription(restaurant.getDescription());
        restaurant.setAddress(restaurant.getAddress());
        repository.save(restaurant);
    }
}
