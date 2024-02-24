package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.*;

@Entity
public class Rewards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer points;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Rewards() {

    }

    public Rewards(Integer points, User user) {
        this.points = points;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
