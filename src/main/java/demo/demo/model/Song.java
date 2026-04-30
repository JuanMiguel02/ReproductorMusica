package demo.demo.model;

import java.time.Duration;

public class Song {

    private String name;
    private String artist;
    private String album;
    private Duration duration;

    public Song(String name, String artist, String album, Duration duration) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }
    public String getArtist() {
        return artist;
    }
    public String getAlbum() {
        return album;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return name + " - " + artist + " - " + album;
    }

}
