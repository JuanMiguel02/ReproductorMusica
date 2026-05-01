package demo.demo.repository;

import demo.demo.model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongRepository {

    private final ObservableList<Song> songs = FXCollections.observableArrayList();
    private static SongRepository instance;

    private SongRepository(){}

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


}
