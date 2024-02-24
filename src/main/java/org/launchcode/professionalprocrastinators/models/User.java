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


    @OneToMany(mappedBy = "user")
    private List<Likes> likes;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // CascadeType.ALL makes it so that if the user is deleted, it will delete the packing lists as well. Update, create and everytihng is updated as well
    private List<PackingList> packingLists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Rewards> rewardsList;

    //No Args constructor for validation
    public User(){
    }

    public User(@NotNull String username, @NotNull String name, @NotNull String email, @NotNull String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.passwordHash = encoder.encode(password);
    }

    public List<PackingList> getPackingLists() {
        return packingLists;
    }

    public void setPackingLists(List<PackingList> packingLists) {
        this.packingLists = packingLists;
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



