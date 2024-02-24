package org.launchcode.professionalprocrastinators.controllers;

import jakarta.servlet.http.*;
import jakarta.validation.*;
import org.launchcode.professionalprocrastinators.models.*;
import org.launchcode.professionalprocrastinators.models.data.UserRepository;
import org.launchcode.professionalprocrastinators.models.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserAuthentication {
    @Autowired
    private UserRepository userRepository;
    //session key to store the user information
    private static final String userSessionKey = "user";

    // uses the session key to get verify the user
    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }
        // retrieve user from the database using user id stored in the session key
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    // once verified this sets the user in the session
    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    // handler to display the registration form
    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    // post mapping processes the registration form for validation and for submission
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors, HttpServletRequest request, Model model) {
        System.out.println(errors);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }
// provides a check to see if the user already exists
        User existingUser = userRepository.findByUsername(registerFormDTO.getUsernameOrEmail());

        if (existingUser != null) {
            errors.rejectValue("username", "username.already exists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "register";
        }
// validates if that the password matches in both fields on the registration form
        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }
// this creates a new user from the confirmed valid registration form and enters their information to the database then redirects to the home page
        User newUser = new User(registerFormDTO.getUsernameOrEmail(), registerFormDTO.getName(), registerFormDTO.getEmail(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:/";
    }

    //handler to display login form
    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        Boolean notLoggedIn = true;
        model.addAttribute("notLoggedIn", notLoggedIn);
        return "login";
    }

    //  handler to validate the login information
    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        User theUser;
//checks for '@' in the field to determine if it is an email or username
        if (loginFormDTO.getUsernameOrEmail().contains("@")) {
// retrieves information based on successful email search
            theUser = userRepository.findByEmail(loginFormDTO.getUsernameOrEmail());
// validates if the email exists in the database
            if (!loginFormDTO.getUsernameOrEmail().equals(theUser.getEmail())) {
                errors.rejectValue("email", "email.invalid", "The given email does not exist");
                model.addAttribute("title", "Log In");
                return "login";
            }
//  retrieves user by username after failed email search
        } else {
            theUser = userRepository.findByUsername(loginFormDTO.getUsernameOrEmail());
        }

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }
//verifies correct password
        String password = loginFormDTO.getPassword();
        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }
// call to set the user in session after successful login validation from above
        setUserInSession(request.getSession(), theUser);

        return "redirect:";
    }
//handler for log out to end user session and invalidate current session
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}

// new code above
