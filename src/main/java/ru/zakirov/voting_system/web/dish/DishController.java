package ru.zakirov.voting_system.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voting_system.model.Dish;
import ru.zakirov.voting_system.repository.DishRepository;
import ru.zakirov.voting_system.repository.RestaurantRepository;

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
    private final UniqueNameOfDishValidator validatorDish;


    public DishController(DishRepository repository, RestaurantRepository restaurantRepository, UniqueNameOfDishValidator validatorDish) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.validatorDish = validatorDish;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validatorDish);
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
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, int restaurantId) {
        log.info("create{}", dish);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.findById(restaurantId).get());
        Dish created = repository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} with id {}", dish, id);
        assureIdConsistent(dish, id);
        repository.save(dish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }
}