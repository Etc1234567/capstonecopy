package org.launchcode.professionalprocrastinators.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.launchcode.professionalprocrastinators.controllers.parser.LocationInfoParser;
import org.launchcode.professionalprocrastinators.controllers.parser.WeatherParser;
import org.launchcode.professionalprocrastinators.models.*;
import org.launchcode.professionalprocrastinators.models.data.VacationRepository;
import org.launchcode.professionalprocrastinators.service.LocationKeyServiceImpl;
import org.launchcode.professionalprocrastinators.service.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

//The API key in application properties will not work and is not the real API key, go to https://developer.accuweather.com/ and create an account to get your own API key
//The current code requests and receives the API responses it needs, however, it does not deserialize the information (it won't put them into the variables in the models)

//Controls the get weather form, and also controls the view weather, uses the service classes to make get requests to the apis
//Has 2 calls to the api, one for location information and one for weather information. Before it does weather information it also checks to make sure locationinformation != null
//It maps the Json using object mapper
//It requires the location key to call to the second api so that's why we had to call twice.
//Object mapper is a class from the Jackson Library, it helps with reading and writing JSON, and can help with conversions
//It also throws errors and has catches in place to make sure it's working correctly
//If you see a System.Out.Println it is so I can tell how far the controller has functioned and where it has stopped.

@Controller
public class WeatherController {
//    Handles user input between Model and View
    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    LocationKeyServiceImpl locationKeyService;


    @Autowired
    WeatherServiceImpl weatherService;




//Creates the form for inputting weather
    @RequestMapping("/get-weather")
    public String weatherForm(Model model){
        List<Vacation> vacations = vacationRepository.findAll();
        model.addAttribute("vacations",vacations);
        return "get-weather";

    }


    @PostMapping("/get-weather")
    public String getWeather(@RequestParam("vacationName") String vacationName, Model model, RedirectAttributes redirectAttributes) {
        try {
//   calls the location service to get the location info using vacationName as a parameter
           List<Object> locationInformation = locationKeyService.getLocationInfo(vacationName);
            System.out.println(locationInformation.get(0));
//            sets the JSON as an object
            Object keyObject = locationInformation.get(0);
            ObjectMapper mapper = new ObjectMapper();
//          uses the object mapper to convert the value of key object into string
            Map<String, Object> map = mapper.convertValue(keyObject, Map.class);
            System.out.println(map.get("Key"));
//          Sets the variable key to the JSON value of "Key"
            String key = (String) map.get("Key");
            LocationInformation locationInfo = new LocationInformation();
//          Sets the key string to the key variable in locationInfo class
            locationInfo.setKey(key);
//            checks to make sure locationInformation is not null, I should eventually change it to check if empty
            if (locationInformation != null) {
                System.out.println(locationInformation);
//              Instantiates the jsonString to call weather service and make the request for info, uses location info as a parameter
                String jsonString = weatherService.getWeatherInfo(locationInfo);
                System.out.println(jsonString);
//              adds jsonString to model
                model.addAttribute("jsonString", jsonString);
//                uses redirectAttribute to be able to access jsonString in the viewWeather method, this adjusts the scope
                redirectAttributes.addFlashAttribute("jsonString", jsonString);

//returns to view-weather
                return "redirect:/view-weather";

            } else {
                model.addAttribute("error", "Location Information not available");
                System.out.println("problem 1");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error getting Weather Information");
            System.out.println("problem 2");
        }

        return "redirect:/view-weather";
    }
//    gets the jsonString from above
    @GetMapping("/view-weather")
    public String viewWeather(@ModelAttribute("jsonString") String jsonString, Model model) {
//        not sure that I need to add to model, but currently trying it. After trying, it still doesn't work.
        model.addAttribute(jsonString);
        return "view-weather";
    }



}


//Code I've changed, keeping it in case I need it later
//                ObjectMapper mapper1 = new ObjectMapper();
//                Map<String, Object> map1 = mapper1.convertValue(weatherInformation.get(0), Map.class);
//                String jsonString = mapper1.writeValueAsString(map1);
//                Object temperature = map1.get("Temperature");
//                ObjectMapper mapper2 = new ObjectMapper();
//                Map<String, Object> tempMap = mapper2.convertValue(temperature, Map.class);
//                Object day= map1.get("Day");
//                ObjectMapper mapper3 = new ObjectMapper();
//                Map<String, Object> dayMap = mapper3.convertValue(day, Map.class);
//                Object minimum = tempMap.get("Minimum");
//                ObjectMapper mapper4 = new ObjectMapper();
//                Map<String, Object> minMap = mapper4.convertValue(minimum, Map.class);
//                Object maximum = tempMap.get("Maximum");
//                ObjectMapper mapper5 = new ObjectMapper();
//                Map<String, Object> maxMap = mapper5.convertValue(maximum, Map.class);
//
//
//                String date = (String) map1.get("Date");
//                double minTemperatureVal = (double) minMap.get("Value");
//                String temperatureUn = (String) minMap.get("Unit");
//                double maxTemperatureVal = (double) maxMap.get("Value");
//                int icon = (int) dayMap.get("Icon");
//                String iconPhrase = (String) dayMap.get("IconPhrase");
//                boolean hasPrecipitation = (boolean) dayMap.get("HasPrecipitation");
//                WeatherInformation weatherInfo = new WeatherInformation();
//                WeatherInformation.Day.setIcon(icon);
//                WeatherInformation.Day.setIconPhrase(iconPhrase);
//                WeatherInformation.Temperature.Maximum.setUnit(temperatureUn);
//                WeatherInformation.Temperature.Maximum.setValue(maxTemperatureVal);
//                WeatherInformation.Temperature.Minimum.setValue(minTemperatureVal);
//                WeatherInformation.DailyForecast.setDate(date);
//                WeatherInformation.Day.setHasPrecipitation(hasPrecipitation);
//                System.out.println("Correctly retrieved weatherJSON");
//                model.addAttribute("weatherInfo", weatherInformation);