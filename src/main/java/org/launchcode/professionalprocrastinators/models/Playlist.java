package org.launchcode.professionalprocrastinators.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Playlist {

    @Id
    @GeneratedValue
    private int id;

//    @OneToMany(mappedBy = "Vacation")
    private String spotifyLink;




    public static String spotifyLinkMaker(String playlistLink) {
        //pattern compiles data into a compressed state which it can use Matcher to assess the data quicker than un-compiled
        Pattern pattern = Pattern.compile("/playlist/(\\w+)");
        Matcher matcher = pattern.matcher(playlistLink);
        //if a match is found,
        if (matcher.find()) {
            return "https://open.spotify.com/embed/playlist/" + matcher.group(1) + "?utm_source=generator";
        } else {
            return "Playlist not Found";
        }
    }


    public String getSpotifyLinks() {
        return spotifyLink;
    }

    public void setSpotifyLinks(String spotifyLinks) {this.spotifyLink = spotifyLinkMaker(spotifyLink); }



}
