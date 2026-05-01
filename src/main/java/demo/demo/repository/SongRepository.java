package demo.demo.repository;

import demo.demo.model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.List;

public class SongRepository {

    private final ObservableList<Song> songs = FXCollections.observableArrayList();
    private static SongRepository instance;

    private SongRepository(){
        loadSongsExampe();
    }

    public static SongRepository getInstance(){
        if(instance == null){
            instance = new SongRepository();
        }
        return instance;
    }

    public boolean addSong(Song song){
        return songs.add(song);
    }

    public List<Song> getSongs(){
        return songs;
    }

    public boolean removeSong(Song song){
        return songs.remove(song);
    }

    private void loadSongsExampe(){
        Song song1 = new Song("Song 1", "Artist 1", "Album 1", Duration.ofMinutes(3));
        song1.setFilePath("src/main/resources/music/TheAdultsAreTalking.mp3");

        Song song2 = new Song("Song 2", "Artist 2", "Album 2", Duration.ofMinutes(3));
        song2.setFilePath("src/main/resources/music/BeautySchool.mp3");

        addSong(song1);
        addSong(song2);
    }

}
