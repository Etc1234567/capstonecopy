package org.launchcode.professionalprocrastinators.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.professionalprocrastinators.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    UserAuthentication userAuthentication;

    @GetMapping("/contact")
    public String showContactForm(Model model, HttpServletRequest request) {
        model.addAttribute("contact", new Contact());
        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);
        Boolean notLoggedIn = (user == null);
        model.addAttribute("notLoggedIn", notLoggedIn);
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(Contact contact) {
        // Process the contact form submission


// redirect to the contact form with a success message
        return "redirect:/contact?success";
    }

    /* information for where email is to be sent
    private void sendEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("nomadnirvana@gmail.com");
        message.setSubject("New Contact Form Submission");
        message.setText("Name: " + contact.getName() + "\nEmail: " + contact.getEmail() + "\nMessage: " + contact.getMessage());

        javaMailSender.send(message);
    }

     */
}
