package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "user")
    private List<Likes> likes;


    @NotNull
       private String username;

    @NotBlank
    @NotEmpty
    @NotNull

    @Size(min = 5, max = 16, message = "Password must be between 5 and 16 characters")
        private String password;
    @NotNull
       private String name;
    @NotNull
    @Email(message = "Email must be valid.")
        private String email;
    @NotNull
        private String location;
        private int numOfVacations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumOfVacations() {
        return numOfVacations;
    }

    public void setNumOfVacations(int numOfVacations) {
        this.numOfVacations = numOfVacations;
    }
    @Override
    public String toString() {
        return "Username: '" + username + "', Name: '" + name + "', Location: '" + location + "', Vacations Taken: " + numOfVacations;
    }
}



