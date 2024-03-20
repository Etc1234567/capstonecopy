package org.launchcode.professionalprocrastinators.controllers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.professionalprocrastinators.models.Activity;
import org.launchcode.professionalprocrastinators.models.Itinerary;
import org.launchcode.professionalprocrastinators.models.User;
import org.launchcode.professionalprocrastinators.models.data.ActivityRepository;
import org.launchcode.professionalprocrastinators.models.data.ItineraryRepository;
import org.launchcode.professionalprocrastinators.models.data.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.launchcode.professionalprocrastinators.models.Vacation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


@Controller
public class HomeController {

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    UserAuthentication userAuthentication;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("title", "My Vacations");
        model.addAttribute("vacations", vacationRepository.findAll());
        model.addAttribute("activities", activityRepository.findAll());
        return "index";
    }

    @PostMapping(value= "/")
        public String processVacationCountdown(Model model){
        model.addAttribute("vacations", vacationRepository.findAll());
        model.addAttribute("activities", activityRepository.findAll());

        return "index";
    }

    @GetMapping(value = "add-vacation")
    public String displayAddVacationForm(Model model) {
        model.addAttribute("title", "Add Vacation");
        return "add-vacation";
    }

    @PostMapping("add-vacation")
    public String processAddVacationForm(@RequestParam String vacationName,
                                         @RequestParam String countries,
                                         @RequestParam(required = false) String vacationState,
                                         @RequestParam(required = false) LocalDateTime vacationDate) {

            vacationRepository.save(new Vacation(vacationName, countries, vacationState, vacationDate));
            return "redirect:";
    }

    @GetMapping("delete-vacation")
    public String displayDeleteVacationForm(Model model) {
        model.addAttribute("title", "Delete Vacation");
        model.addAttribute("vacations", vacationRepository.findAll());
        return "/delete-vacation";
    }

    @PostMapping("delete-vacation")
    public String processDeleteVacationForm(@RequestParam(required = false) int deletedVacation) {
        vacationRepository.deleteById(deletedVacation);
        return "redirect:/";
    }

    @GetMapping("edit-vacation")
    public String displayEditVacationForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);
        model.addAttribute("title", "Edit Vacation");
        model.addAttribute("vacations", vacationRepository.findAll());
        return "/edit-vacation";
    }

    @PostMapping("edit-vacation")
    public String processEditVacationForm(@RequestParam int selectedVacation,
                                          @RequestParam String vacationName,
                                          @RequestParam String countries,
                                          @RequestParam (required =false) String vacationState,
                                          @RequestParam LocalDateTime vacationDate) {

        Vacation editedVacation = vacationRepository.findById(selectedVacation).orElse(new Vacation());

//        For now, the user must re-enter all of their desired vacation information to update the record. In the future, we will have the site auto-fill this information and they will only need to update what is incorrect.
            editedVacation.setCity(vacationName);
            editedVacation.setCountry(countries);
            editedVacation.setState(vacationState);
            editedVacation.setVacationDate(vacationDate);
            vacationRepository.save(editedVacation);

        return "redirect:/";
    }

    @GetMapping("add-activity")
    public String displayAddActivityForm(Model model) {
        model.addAttribute("title", "Add Trip Inspiration");
        model.addAttribute("vacations", vacationRepository.findAll());
        return "/add-activity";
    }

    @PostMapping("add-activity")
    public String processAddActivityForm(@RequestParam String title,
                                         @RequestParam String url,
                                         @RequestParam int vacationId,
                                         @RequestParam(required = false) String notes) {

//       First, find the vacation we want to link the activity to, then create the activity, then create and set the embedURL, and finally save it in the activity repository and on the linked vacation object.

        Vacation linkedVacation = vacationRepository.findById(vacationId).orElse(new Vacation());

        Activity addedActivity = new Activity(title, url, linkedVacation, notes);

        String embedUrl= addedActivity.embedUrl(url);

        addedActivity.setEmbedUrl(embedUrl);

        activityRepository.save(addedActivity);

        linkedVacation.getActivities().add(addedActivity);

        return "redirect:/";
    }

    @GetMapping("delete-activity")
    public String displayDeleteActivityForm(Model model) {
        model.addAttribute("title", "Delete Activity");
        model.addAttribute("activities", activityRepository.findAll());
        return "/delete-activity";
    }

    @PostMapping("delete-activity")
    public String processDeleteActivityForm(@RequestParam(required = false) int deletedActivity) {
        activityRepository.deleteById(deletedActivity);
        return "redirect:/";
    }

    @GetMapping("/view/{vacationId}")
    public String displayViewVacation(Model model, @PathVariable int vacationId) {

        model.addAttribute("agendaItems", itineraryRepository.findAll());

        Optional<Vacation> optVacation = vacationRepository.findById(vacationId);
        ArrayList<Activity> filteredActivities = new ArrayList<>();
        ArrayList<Itinerary> filteredAgenda = new ArrayList<>();

        if (optVacation.isPresent()) {
            Vacation vacation = (Vacation) optVacation.get();
            model.addAttribute("vacation", vacation);

            for (Activity activity: activityRepository.findAll()){
                 Vacation currentVacation = activity.getLinkedVacation();
                 int currentId = currentVacation.getId();

                        if (currentId == vacationId) {
                            filteredActivities.add(activity);
                        }
            }
            model.addAttribute("activities", filteredActivities);

            for (Itinerary itinerary: itineraryRepository.findAll()){
                Vacation currentVacation = itinerary.getLinkedVacation();
                int currentId = currentVacation.getId();

                if (currentId == vacationId) {
                    filteredAgenda.add(itinerary);
                }
            }
            model.addAttribute("filteredAgendaItems", filteredAgenda);

            return "view"; }
        else {
            return "redirect:../";
        }

    }

}

