package wad.myalbums;

import java.util.List;

public class Album {

    private String artist;
    private String name;
    private List<String> tracks;

    public Album(String artist, String name, List<String> tracks) {
        this.artist = artist;
        this.name = name;
        this.tracks = tracks;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public List<String> getTracks() {
        return tracks;
    }
}
