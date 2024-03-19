package org.launchcode.professionalprocrastinators.controllers;

import org.launchcode.professionalprocrastinators.models.Vacation;
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
    private VacationRepository vacationRepository;

    @Autowired
    UserAuthentication userAuthentication;

    @GetMapping("/itinerary")
    public String viewItinerary(Model model) {
        model.addAttribute("title", "Create Itinerary");
        model.addAttribute("vacations", vacationRepository.findAll());
        return "itinerary";
    }

    @PostMapping("/itinerary")
    public String processAddItinerary(@RequestParam String vacationName,
                                         @RequestParam String countries,
                                         @RequestParam(required = false) String vacationState,
                                         @RequestParam(required = false) LocalDateTime vacationDate) {

        vacationRepository.save(new Vacation(vacationName, countries, vacationState, vacationDate));
        return "redirect:";
    }
}
