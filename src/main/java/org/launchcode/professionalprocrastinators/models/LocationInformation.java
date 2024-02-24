package org.launchcode.professionalprocrastinators.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//This is my model for the location information, it is set up similar to how the JSON will be sent when we request to get it from the API
//It includes JSON Property Annotations to map the JSON value to the variable, it also includes an ignore properties annotation to ignore the variables that were unnecessary

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationInformation {
    private List<LocationInformation> locationList;
    private String key;
    private String localizedName;
    private Region region;
    private TimeZone timeZone;

    @JsonProperty("Key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("LocalizedName")
    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public static class Region {
        private String englishName;

        @JsonProperty("EnglishName")
        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }
    }

    public static class TimeZone {
        private String code;
        private String name;

        @JsonProperty("Code")
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @JsonProperty("Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<LocationInformation> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationInformation> locationList) {
        this.locationList = locationList;
    }
}

