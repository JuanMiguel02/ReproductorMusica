package demo.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements SongCollection{

    private ObservableList<Song> songs = FXCollections.observableArrayList();

    public void addSong(Song song) {
        songs.add(song);
    }

    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public SongIterator createIterator() {
        return new PlaylistIterator(this);
    }
}
