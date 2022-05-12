package ru.zakirov.voting_system.web.meal;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakirov.voting_system.model.Dish;
import ru.zakirov.voting_system.repository.DishRepository;
import ru.zakirov.voting_system.web.GlobalExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueDescriptionValidatorDish implements Validator {
    private DishRepository dishRepository;
    private HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Dish.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Dish dish = (Dish) target;
        if (StringUtils.hasText(dish.getName())) {
            dishRepository.getByNameIgnoreCase(dish.getName())
                    .ifPresent(dbMeal -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbMeal.id();

                            if (dish.getId() != null && dbId == dish.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }
                        errors.rejectValue("description", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_DESCRIPTION_OF_MEAL);
                    });
        }
    }
}
