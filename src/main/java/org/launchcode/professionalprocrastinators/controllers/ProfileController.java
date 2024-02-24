package org.launchcode.professionalprocrastinators.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.ui.Model;
import org.launchcode.professionalprocrastinators.models.User;
import org.launchcode.professionalprocrastinators.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.launchcode.professionalprocrastinators.models.data.dto.LoginFormDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//gets the user with the HttpServlet Request, checks for auth, and displays the profile template with the following fields.
@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;
    // TODO: Delete button, when clicked takes to a confirm page, yes gives back true, no gives false and then if true = delete, no goes back to profile page
    @Autowired
    UserAuthentication userAuthentication;

    @GetMapping("/profile")
    public String viewProfile(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("passwordHash", user.getPasswordHash());
        return "profile";
    }

    @GetMapping("/delete_profile")
    public String viewDeleteConfirmation() {
        return "delete-confirmation";
    }

    @PostMapping("/delete_profile")
    public String processDeleteConfirmation(@RequestParam String isDeleted, HttpServletRequest request, Model model) {
        System.out.println(isDeleted);
        boolean isDeletedValue = Boolean.valueOf(isDeleted);
        System.out.println(isDeletedValue);
        if (isDeletedValue) {
            HttpSession session = request.getSession();
            User user = userAuthentication.getUserFromSession(session);
            userRepository.deleteById(user.getId());
            return "redirect:/account_deleted";
        } else return "redirect:/profile";
    }

    @GetMapping("/account_deleted")
    public String accountDeletedSuccessfully(Model model) {
        Boolean notLoggedIn = true;
        model.addAttribute("notLoggedIn", notLoggedIn);
        return "account-deleted";
    }
}


//TODO: Add VacationsLiked and update html, Index?
//TODO: Add Top number of likes for the vacations the user created
//If I had more time I would like to create a followers MVC and repository, and display followers here, I would also like to disply top liked vacation and update the styling of the view.