package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.*;

import java.util.ArrayList;
import java.util.List;
//This serves as a model for profile, but also for other Controllers especially for auth.

@Entity
public class User extends AbstractEntity{

    private static User user;

    @NotBlank
    @Size(min = 3, max = 16)
       private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank
    private String passwordHash;

    private String location;

    private int numOfVacations;

    //No Args constructor for validation
    public User(){
    }

    public User(@NotNull String username, @NotNull String name, @NotNull String email, @NotNull String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.passwordHash = encoder.encode(password);
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Boolean isMatchingPassword(String password){
        return encoder.matches(password, passwordHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public void setId(int i) {
    }
}



