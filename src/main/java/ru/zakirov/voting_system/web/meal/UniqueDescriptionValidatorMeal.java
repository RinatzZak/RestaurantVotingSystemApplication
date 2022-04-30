package ru.zakirov.voting_system.web.meal;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zakirov.voting_system.model.Meal;
import ru.zakirov.voting_system.repository.MealRepository;
import ru.zakirov.voting_system.web.GlobalExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueDescriptionValidatorMeal implements Validator {
    private MealRepository mealRepository;
    private HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Meal meal = (Meal) target;
        if (StringUtils.hasText(meal.getDescription())) {
            mealRepository.getByDescriptionIgnoreCase(meal.getDescription())
                    .ifPresent(dbMeal -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbMeal.id();

                            if (meal.getId() != null && dbId == meal.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }
                        errors.rejectValue("description", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_DESCRIPTION_OF_MEAL);
                    });
        }
    }
}
