package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.*;
import org.launchcode.professionalprocrastinators.models.data.UserRepository;
//This is my model for the Like Button, it has variables I need along with getters and setters and relationships.
@Entity
@Table(name ="likes")
public class Likes {
    @Id
    @GeneratedValue
    private int id;
//    Cascade keeps updates consistent between all tables
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    private int likes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
