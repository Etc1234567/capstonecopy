package org.launchcode.professionalprocrastinators.controllers.parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.professionalprocrastinators.models.WeatherInformation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//This is the weather parser, it helps to parse the JSON for the weather info, it uses the objectMapper that I talked about in Weather Controller.
//Readvalue is a method in objectmapper, it helps to convert the JSON to java, in our code it puts it into an array of objects
//Arrays.asList converts the array into a list of objects, jsonArray1
public class WeatherParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static List<Object> parseWeatherJson(String weatherJsonString) throws IOException{
        try {
            List<Object> jsonArray1 = Arrays.asList(objectMapper.readValue(weatherJsonString, Object[].class));
            return jsonArray1;
        } catch (IOException e) {
            String exceptionDetails = e.toString();
            System.out.println("Exception Details: " + exceptionDetails);
            return null;
        }
    }
}
