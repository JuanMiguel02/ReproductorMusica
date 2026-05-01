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
    public List<Song> getSongs(){
        return songRepository.getSongs();
    }

    public List<Song> filterSongs(List<Song> allSongs, String query){
        if(query == null || query.isEmpty()){
            return allSongs;
        }
        String lowerCaseFilter = query.toLowerCase();
        return allSongs.stream()
                .filter(song -> song.getName().toLowerCase().contains(lowerCaseFilter)
                        || song.getArtist().toLowerCase().contains(lowerCaseFilter)
                        || song.getAlbum().toLowerCase().contains(lowerCaseFilter))
                .toList();
    }

}
