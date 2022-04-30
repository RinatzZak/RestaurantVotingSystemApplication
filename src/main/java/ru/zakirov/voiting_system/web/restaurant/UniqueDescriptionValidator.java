package ru.zakirov.voiting_system.web.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakirov.voiting_system.model.Restaurant;
import ru.zakirov.voiting_system.repository.RestaurantRepository;
import ru.zakirov.voiting_system.web.GlobalExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueDescriptionValidator implements Validator {
    private final RestaurantRepository restaurantRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Restaurant restaurant = (Restaurant) target;
        if (StringUtils.hasText(restaurant.getDescription())) {
            restaurantRepository.getByDescriptionIgnoreCase(restaurant.getDescription())
                    .ifPresent(dbRestaurant ->
                    {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbRestaurant.id();

                            if (restaurant.getId() != null && dbId == restaurant.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }
                        errors.rejectValue("description", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_DESCRIPTION_OF_RESTAURANT);
                    });
        }
    }
}
