package org.launchcode.professionalprocrastinators.service;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.launchcode.professionalprocrastinators.controllers.parser.LocationInfoParser;
import org.launchcode.professionalprocrastinators.models.LocationInformation;
import org.launchcode.professionalprocrastinators.models.Vacation;
import org.launchcode.professionalprocrastinators.models.data.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//In my research, it said that due to polymorphism it is best to set up an interface and a class that implements that interface.
//This is the Service annotation because it helps with the logic for the location api
//This is the class for location service
@Service
public class LocationKeyServiceImpl implements LocationKeyService{
    // Logic to make API request, parse response, and return Weather object

    private final String apiKey;
    private final RestTemplate restTemplate;
    public static String location;
    @Autowired
    static VacationRepository vacationRepository;
//    This is a constructor, it initializes some of the things below
    public LocationKeyServiceImpl(RestTemplate restTemplate, @Value("${weather.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }
//This builds the url that our Api needs, it uses the url, the location parameter, and the apikey from app.properties
    private String buildLocUrl(String location) {
        String accuWeatherLocationUrl = "http://dataservice.accuweather.com/locations/v1/cities/search";
        return accuWeatherLocationUrl + "?q=" + location + "&apikey=" + apiKey;
    }


    public List<Object> getLocationInfo(String vacationName) {
//tries to execute code and has a handler in case it catches it
        try {
//            builds the url and checks it in console
            String apiUrl = buildLocUrl(vacationName);
            System.out.println(apiUrl);
//            response entity is for the response from api, rest template is used to HTTP get the response
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
//This makes sure that the status on the HTTP request is ok, and if not the else prints what the code is
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                //                    gets body of HTTP response and sets it as String
                String locationJsonString = responseEntity.getBody();
                System.out.println(locationJsonString);
//                calls the locationParser to parse through the JSON string
                return LocationInfoParser.parseLocationJson(locationJsonString);
            } else {
                System.out.println("Error calling the location API. Status code: " + responseEntity.getStatusCode());
                return null;
            }
        } catch (RestClientException | IOException e) {
//            prints to console
            e.printStackTrace();
            System.out.println("Hi");
            return  null;
        }
    }

    }


