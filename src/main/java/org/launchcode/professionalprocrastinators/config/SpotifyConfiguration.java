//package org.launchcode.professionalprocrastinators.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import se.michaelthelin.spotify.SpotifyApi;
//import se.michaelthelin.spotify.SpotifyHttpManager;
//
//import java.net.URI;
//
//@Service
//public class SpotifyConfiguration {
////    @Value("${redirect.server.ip}")
////    private String customip;
//
//    public SpotifyApi getSpotifyObject() {
//        URI redirectedURL = SpotifyHttpManager.makeUri(customip + "/api/get-user-code/");
//
//        return new SpotifyApi.Builder().setClientId("").setClientSecret("").setRedirectUri(redirectedURL).build();
//    }
//
//}
