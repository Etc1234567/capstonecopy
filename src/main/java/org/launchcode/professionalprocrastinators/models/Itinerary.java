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
}
