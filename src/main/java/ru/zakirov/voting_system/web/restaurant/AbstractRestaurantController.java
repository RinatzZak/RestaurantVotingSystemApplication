package ru.zakirov.voting_system.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.zakirov.voting_system.repository.RestaurantRepository;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository repository;


}
