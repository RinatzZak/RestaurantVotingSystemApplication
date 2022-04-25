package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "vote")
@NoArgsConstructor
@Getter
@Setter
public class Vote extends BaseEntity {

    @Column(name = "time_added", columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalTime timeAdded = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"address", "menu"})
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public Vote(Integer id, LocalTime timeAdded, Restaurant restaurant, User user) {
        super(id);
        this.timeAdded = timeAdded;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(LocalTime time, Restaurant restaurant, User user) {
        this.timeAdded = time;
        this.restaurant = restaurant;
        this.user = user;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "time = " + timeAdded + ")";
    }
}
