package ru.zakirov.voting_system.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voting_system.model.Meal;
import ru.zakirov.voting_system.model.Menu;
import ru.zakirov.voting_system.repository.MealRepository;
import ru.zakirov.voting_system.repository.MenuRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static ru.zakirov.voting_system.util.validation.ValidationUtil.assureIdConsistent;
import static ru.zakirov.voting_system.util.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {
    static final String REST_URL = "/api/admin/meals";

    private final MealRepository repository;

    private final MenuRepository menuRepository;

    private final UniqueDescriptionValidatorMeal validatorMeal;


    public MealRestController(MealRepository repository, MenuRepository menuRepository, UniqueDescriptionValidatorMeal validatorMeal) {
        this.repository = repository;
        this.menuRepository = menuRepository;
        this.validatorMeal = validatorMeal;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validatorMeal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> get(@PathVariable int id) {
        log.info("get{}", id);
        return ResponseEntity.of(Objects.requireNonNull(repository.findById(id)));
    }

    @GetMapping
    public List<Meal> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal) {
        log.info("create{}", meal);
        checkNew(meal);
        Meal created = repository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id) {
        log.info("update {} with id {}", meal, id);
        assureIdConsistent(meal, id);
        meal.setDescription(meal.getDescription());
        meal.setPrice(meal.getPrice());
        repository.save(meal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        List<Menu> menus = menuRepository.findAll();
        Meal meal = repository.getById(id);
        for (Menu m : menus) {
            m.getMeals().remove(meal);
            menuRepository.saveAndFlush(m);
        }
        repository.delete(meal);
    }
}