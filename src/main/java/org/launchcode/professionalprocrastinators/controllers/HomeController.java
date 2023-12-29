package org.launchcode.professionalprocrastinators.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.launchcode.professionalprocrastinators.models.Vacation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    private static List<Vacation> vacations = new ArrayList<>();

    @GetMapping(value = "/")
    public String index(Model model) {
    model.addAttribute("title", "My Vacations");
    model.addAttribute("vacations", vacations);
    return "index";
    }

    //Will add a handler for the vacation date once database is set up.
//    @PostMapping("/")
//    public String processVacationCountdown(@RequestParam Date dateInput, String selectedVacation){
//        vacations.indexOf(selectedVacation)
//    }

    @GetMapping(value= "add-vacation")
    public String displayAddVacationForm(Model model) {
        model.addAttribute("title", "Add Vacation");
        return "add-vacation";
    }

    @PostMapping("add-vacation")
    public String processAddVacationForm(@RequestParam String vacationName,
                                         @RequestParam String vacationCountry,
                                         @RequestParam(required= false) String vacationState){

        vacations.add(new Vacation(vacationName, vacationCountry, vacationState));
        return "redirect:";
    }
}
