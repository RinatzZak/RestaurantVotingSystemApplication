package ru.zakirov.voting_system.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voting_system.model.Dish;
import ru.zakirov.voting_system.repository.DishRepository;
import ru.zakirov.voting_system.repository.RestaurantRepository;
import ru.zakirov.voting_system.to.DishTo;
import ru.zakirov.voting_system.util.DishUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.zakirov.voting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voting_system.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String REST_URL = "/api/admin/meals";

    private final DishRepository repository;

    private final RestaurantRepository restaurantRepository;

    public DishController(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id) {
        log.info("get{}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo, Integer restaurantId) {
        log.info("create{}", dishTo);
        checkNew(dishTo);
        Dish created = DishUtil.createNewFromTo(dishTo);
        created.setRestaurant(restaurantRepository.findById(restaurantId).get());
        repository.save(created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id, Integer restaurantId) {
        log.info("update {} with id {}", dishTo, id);
        assureIdConsistent(dishTo, id);
        Dish dish = repository.getById(id);
        Dish updated = DishUtil.updateFromTo(dish, dishTo);
        updated.setRestaurant(restaurantRepository.findById(restaurantId).get());
        repository.save(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }
}
