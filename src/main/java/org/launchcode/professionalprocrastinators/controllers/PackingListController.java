package org.launchcode.professionalprocrastinators.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.professionalprocrastinators.models.PackingList;
import org.launchcode.professionalprocrastinators.models.User;
import org.launchcode.professionalprocrastinators.models.data.PackingListRepository;
import org.launchcode.professionalprocrastinators.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Controller
public class PackingListController {

    @Autowired
    UserAuthentication userAuthentication;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PackingListRepository packingListRepository;


    @GetMapping("/packing-list")
    public String displayPackingListForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);
        model.addAttribute("user", user);
        model.addAttribute(new PackingList());
        return "packing-list";
    }

    //    TODO:create an ArrayList in the Post for packinglist, then think of a way to have the javascript add strings to that array (you might have to make the array in JS) when the button to add the item is clicked.
//    you might have to figure out a way that on click it wipes the input and so that on the save click it pushes it to the post controller.

    @PostMapping(value = "/packing-list")
    public String processPackingListForm(@RequestParam("jsonData") String jsonData, Model model, HttpServletRequest request) {
        PackingList packingList = new PackingList();
        //initalizing emptpy arrays to put data from the json object in the "try" field
        List<String> arrayOfItems = new ArrayList<>();
        List<Integer> quantityArray = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // objectMapper then arranges the data so that we can do .addAll to put the data in the corrosponding arrays by using the key String as the parameter.
            Map<String, Object> data = objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {});

            arrayOfItems.addAll((List<String>) data.get("arrayOfItems"));
            quantityArray.addAll((List<Integer>) data.get("quantityArray"));
            // catches errors
        } catch (IOException e) {
            e.printStackTrace();
        }
        //grabs user id
        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);
        //saves user id to the list, along with a string of items, and each items quantity.
        if (!arrayOfItems.isEmpty() || !quantityArray.isEmpty()) {
            packingList.setUser(user);
            packingList.setItemQuantity(quantityArray);
            packingList.setPackingItems(arrayOfItems);
            packingListRepository.save(packingList);
            List<PackingList> list = user.getPackingLists();
            list.add(packingList);
            user.setPackingLists(list);
            userRepository.save(user);
            System.out.println("List added");
            return "redirect:/packing_list";
        }

        return "packing-list";
        //TODO: make it redirect to index/packing_list/${list id} // might be hard to do since you need to find a way so that only users that created it can view it.
    }

}
