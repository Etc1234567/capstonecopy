package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Vacation {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    private List<Itinerary> agendaItems = new ArrayList<>();

    @OneToMany
    private List<Activity> activities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String city;

    private String country;

    private String state;

    private LocalDateTime vacationDate;

    @Override
    public String toString() {
        return "Vacation{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", vacationDate=" + vacationDate +
                '}';
    }

    public List<Itinerary> getAgendaItems() {
        return agendaItems;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public LocalDateTime getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(LocalDateTime vacationDate) {
        this.vacationDate = vacationDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public Vacation(String city, String country, String state, LocalDateTime vacationDate) {
        this.city = city;
        this.country=country;
        this.state= state;
        this.vacationDate=vacationDate;
    }

    public Vacation() {}

}
