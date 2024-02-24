package org.launchcode.professionalprocrastinators.controllers;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.professionalprocrastinators.models.Activity;
import org.launchcode.professionalprocrastinators.models.Likes;
import org.launchcode.professionalprocrastinators.models.User;
import org.launchcode.professionalprocrastinators.models.Vacation;
import org.launchcode.professionalprocrastinators.models.data.LikesRepository;
import org.launchcode.professionalprocrastinators.models.data.UserRepository;
import org.launchcode.professionalprocrastinators.models.data.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

//This is the controller for the Like Button, it has a method to show a form it displays with a get request, it has a post request to process that form, and a method to process the get request from the next page that shows results.

@Controller
public class LikeButtonController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    UserAuthentication userAuthentication;
//Display form, gets user from auth, returns likes template with vacations that are visible to the public.
    @GetMapping("/likes")
    public String displayLikesForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);
        model.addAttribute("userId", user.getId());
        model.addAttribute("vacations", vacationRepository.findByVisibility("public"));
        model.addAttribute("likes", new Likes());
        model.addAttribute("user", user);
        return "likes";

    }


//processes the form from above, checks for likes in repository so we can update the likes instead of having repeats, redirects you to view your likes
    @PostMapping("/likes")
    public String processLikesForm(@ModelAttribute Likes likes, @RequestParam("userId") int userId, @RequestParam("vacationID") int vacationId, Model model) {
        User user = userRepository.findById(userId);
        System.out.println("Received userId: " + user.getId());
        System.out.println("Received vacationId: " + vacationId);
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow(() -> new IllegalArgumentException("Invalid vacation ID"));
        Likes existingLikes = likesRepository.findByUserAndVacation(user, vacation);
        if (existingLikes != null) {
            existingLikes.setLikes(existingLikes.getLikes() + 1);
            likesRepository.save(existingLikes);
            return "redirect:/view-likes";
        } else {
            likes.setUser(user);
            likes.setVacation(vacation);
            likes.setLikes(1);
            likesRepository.save(likes);
            model.addAttribute("successMessage", "Liked Successful!");
            return "redirect:/view-likes";
        }
    }

//    This is the handler for the view of your likes, it gets the user using the httpServlet request and then finds the likes in the repository using the user id.
        @GetMapping("/view-likes")
        public String viewLikedVacations (HttpServletRequest request, Model model) {
            HttpSession session = request.getSession();
            User user = userAuthentication.getUserFromSession(session);
            int userId = user.getId();
            System.out.println(userId);
            List<Likes> likedVacations = likesRepository.findByUserId(userId);

            model.addAttribute("likedVacations", likedVacations);
            return "view-likes";
        }

}
