package org.launchcode.professionalprocrastinators.controllers;

import org.launchcode.professionalprocrastinators.models.Nomies;

//@RequestMapping("/profile")
//public class ProfileController {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    VacationRepository vacationRepository;
//
//    @Autowired
//    NomiesRepository nomiesRepository;

//    @GetMapping("/profile")
//    public String viewProfile(Model model, Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            String username = authentication.getUsername();
//            User user = userRepository.findByUsername(username);
//            if (user != null) {
//                List<Vacation> userVacations = vacationRepository.findByUser(user);
//                int numOfVacations = userVacations.size();

//                model.addAttribute("username", username);
//                model.addAttribute("numOfVacations", numOfVacations);
//                return "profile";
//            }
//        } else {
//            return "redirect:/error";
//        }
//        return "redirect:/login";
//    }
//}


//TODO: Add a followers, and update html
//TODO: Add VacationsLiked and update html, Index?
//TODO: Add Top number of likes for the vacations the user created