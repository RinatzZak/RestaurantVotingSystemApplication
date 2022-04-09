package ru.zakirov.voiting_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "votes")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Vote extends BaseEntity {

    @Column(name = "vote")
    private boolean vote;

    @Column(name = "time")
    private LocalTime time;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Vote(Integer id, boolean vote, LocalTime time, Restaurant restaurant, User user) {
        super(id);
        this.vote = vote;
        this.time = time;
        this.restaurant = restaurant;
        this.user = user;
    }
}
