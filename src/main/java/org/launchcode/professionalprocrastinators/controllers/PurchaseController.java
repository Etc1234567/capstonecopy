package org.launchcode.professionalprocrastinators.controllers;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class PurchaseController {

    @RequestMapping("/purchase")
    public String showPurchasePage() {
        return "purchase";
    }

    // parameter requests for purchase form
    @PostMapping("/purchase")
    public String processPurchase(
            @RequestParam String itemName,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String creditCard,
            @RequestParam String expiration,
            @RequestParam String zipCode,
            Model model
    ) {
        // server side validation
        if (!isString(name) || !isString(address)) {
            model.addAttribute("error", "Name and address must be strings.");
            return "purchase";
        }

        if (!isNumber(creditCard) || !isNumber(expiration) || !isNumber(zipCode)) {
            model.addAttribute("error", "Credit card, expiration, and zip code must be numbers.");
            return "purchase";
        }

        // here we would process the card information
        // no actual processing for project just auto confirmation if fields are filled out correctly

        return "purchase-confirmation";
    }

    private boolean isString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isNumber(String value) {
        return value != null && value.matches("\\d+");
    }
}