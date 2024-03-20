package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Itinerary {
    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime dateTime;

    private String location;

    private String notes;

    @OneToOne
    private Vacation linkedVacation;

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Vacation getLinkedVacation() {
        return linkedVacation;
    }

    public void setLinkedVacation(Vacation linkedVacation) {
        this.linkedVacation = linkedVacation;
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "dateTime=" + dateTime +
                ", location='" + location + '\'' +
                ", notes='" + notes + '\'' +
                ", linkedVacation=" + linkedVacation +
                '}';
    }

    public Itinerary(LocalDateTime dateTime, String location, String notes, Vacation linkedVacation) {
        this.dateTime = dateTime;
        this.location = location;
        this.notes = notes;
        this.linkedVacation = linkedVacation;
    }

    public Itinerary() {}
}


