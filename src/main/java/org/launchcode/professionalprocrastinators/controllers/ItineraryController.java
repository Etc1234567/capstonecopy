package org.launchcode.professionalprocrastinators.controllers;

import org.launchcode.professionalprocrastinators.models.Itinerary;
import org.launchcode.professionalprocrastinators.models.Vacation;
import org.launchcode.professionalprocrastinators.models.data.ItineraryRepository;
import org.launchcode.professionalprocrastinators.models.data.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ItineraryController {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    UserAuthentication userAuthentication;

    @GetMapping("/itinerary")
    public String viewItinerary(Model model) {
        model.addAttribute("title", "Create Itinerary");
        model.addAttribute("itinerary", itineraryRepository.findAll());
        return "itinerary";
    }

//    @PostMapping("/itinerary")
//    public String processAddItinerary(@RequestParam LocalDateTime dateTime,
//                                         @RequestParam String location,
//                                         @RequestParam(required = false) String notes,
//                                         @RequestParam(required = false) Vacation linkedVacation) {
//
//        itineraryRepository.save(new Itinerary(dateTime, location, notes, linkedVacation));
//        return "redirect:";
//    }
}
