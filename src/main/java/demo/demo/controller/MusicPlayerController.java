package demo.demo.controller;

import demo.demo.model.Song;
import demo.demo.repository.SongRepository;


import java.util.List;

public class MusicPlayerController {

    private final SongRepository songRepository = SongRepository.getInstance();

    public boolean addSong(Song song){
        return songRepository.addSong(song);
    }
    public boolean removeSong(Song song){
        return songRepository.removeSong(song);
    }
    public List<Song> getSong(){
        return songRepository.getSongs();
    }

}
