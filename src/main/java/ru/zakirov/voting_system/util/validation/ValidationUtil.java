package ru.zakirov.voting_system.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.zakirov.voting_system.HasId;
import ru.zakirov.voting_system.error.IllegalRequestDataException;

import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {

    public static final LocalTime FRONTIER = LocalTime.of(11, 0);

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static void checkTime() {
        if (LocalTime.now().isAfter(FRONTIER)) {
            throw new IllegalRequestDataException("You can't change your vote after 11!");
        }
    }

    public static void checkEmpty(Object e) {
        if (e != null) {
            throw new IllegalRequestDataException("It is already exist!");
        }
    }
}