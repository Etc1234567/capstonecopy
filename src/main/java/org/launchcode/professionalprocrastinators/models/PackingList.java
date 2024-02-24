package org.launchcode.professionalprocrastinators.models;


import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Entity
public class PackingList {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String itemQuantity;

    private String packingItems;

//    @OneToMany(mappedBy = "PackingList")
//    private Vacation vacation;

    //splits the string that was made to avoid using BLOB's in the database for small strings
    //returns back the initial List Array
    private List<String> convertStringToArray(String inputString) {
        return Arrays.asList(inputString.split(", "));
    }
    //same thing as String to List but this also converts it to Integers since it's a numbers array.
    private List<Integer> convertStringToIntegerArray(String inputString) {
        return Arrays.stream(inputString.split(", "))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    public PackingList(){

    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getItemQuantity() {
        return convertStringToIntegerArray(itemQuantity);
    }

    public void setItemQuantity(List<Integer> itemQuantity) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < itemQuantity.size(); i++) {
            stringBuilder.append(itemQuantity.get(i));

            // Append a comma if it's not the last element
            if (i < itemQuantity.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        this.itemQuantity = stringBuilder.toString();
    }


    public List<String> getPackingItems() {
        return convertStringToArray(packingItems);
    }

    public void setPackingItems(List<String> packingItems) {
        this.packingItems = String.join(", ", packingItems);
    }

    public String intArrayToString(List<Integer> numberArray) {
        StringJoiner intArrayToStringArr = new StringJoiner(", ");
        numberArray.forEach(num -> intArrayToStringArr.add(Integer.toString(num)));
        return intArrayToStringArr.toString();
    }


}
//    @NotEmpty
//    @NotBlank
//    HashMap<String, Integer> listItem = new HashMap<String, Integer>();

