package ru.zakirov.voiting_system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Table(name = "votes")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Vote extends BaseEntity {

    @Column(name = "voice", nullable = false, columnDefinition = "bool default true")
    private boolean voice;

    @Column(name = "time", columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalTime time;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Vote(Integer id, boolean voice, LocalTime time, Restaurant restaurant, User user) {
        super(id);
        this.voice = voice;
        this.time = time;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(boolean voice, LocalTime time, Restaurant restaurant, User user) {
        this.voice = voice;
        this.time = time;
        this.restaurant = restaurant;
        this.user = user;
    }
}
