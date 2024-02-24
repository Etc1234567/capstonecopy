package org.launchcode.professionalprocrastinators.controllers.parser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.professionalprocrastinators.models.LocationInformation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;


//This is the location parser, it helps to parse the JSON for the location info, it uses the objectMapper that I talked about in Weather Controller.
//Readvalue is a method in objectmapper, it helps to convert the JSON to java, in our code it puts it into an array of objects
//Arrays.asList converts the array into a list of objects jsonArray
public class LocationInfoParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Object> parseLocationJson(String locationJsonString) throws IOException {
        try {
            List<Object> jsonArray = Arrays.asList(objectMapper.readValue(locationJsonString, Object[].class));
            return jsonArray;
        } catch (IOException e) {
            String exceptionDetails = e.toString();
            System.out.println("Exception Details: " + exceptionDetails);
            return null;
        }
    }
}

