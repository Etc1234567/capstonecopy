package org.launchcode.professionalprocrastinators.service;

import org.launchcode.professionalprocrastinators.controllers.parser.WeatherParser;
import org.launchcode.professionalprocrastinators.models.LocationInformation;
import org.launchcode.professionalprocrastinators.models.WeatherInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
//In my research, it said that due to polymorphism it is best to set up an interface and a class that implements that interface.
//This has the Service annotation because it helps with the logic for the location api
//This is the class for weather service
@Service
public class WeatherServiceImpl implements WeatherService {


    private final String apikey;
    private final RestTemplate restTemplate;
    private String locationKey;

    @Autowired
    public WeatherServiceImpl(@Value("${weather.api.key}") String apiKey, RestTemplate restTemplate) {
        this.apikey = apiKey;
        this.restTemplate = restTemplate;
    }
    private String buildWeatherUrl(LocationInformation locationInformation){
        String accuWeatherForecastUrl = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
        String endApi = "?apikey=" + apikey;
        System.out.println(locationInformation);
        locationKey = (String) locationInformation.getKey();
//        locationKey = locationInformation[0].Key;
        System.out.println(locationKey);
        return accuWeatherForecastUrl + locationKey + endApi;
    }

    public String getWeatherInfo(LocationInformation locationInformation) {
        //tries to execute code and has a handler in case it catches it
        try {
//            builds the url and checks it in console
            String apiUrl = buildWeatherUrl(locationInformation);
            System.out.println(apiUrl);
            //            response entity is for the response from api, rest template is used to HTTP get the response
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
            //This makes sure that the status on the HTTP request is ok, and if not the else prints what the code is
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                    gets body of HTTP response and sets it as String
                String weatherJsonString = responseEntity.getBody();
                System.out.println(weatherJsonString);
//                    calls the weatherParser to parse through the JSON string
                return weatherJsonString;
            } else {
                System.out.println("Error calling the weather API. Status code: " + responseEntity.getStatusCode());
            }

//        } catch (IOException e) {
////takes the exception and makes it a runtime exception so it doesn't require more
//            throw new RuntimeException(e);
//        }
            return null;
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
