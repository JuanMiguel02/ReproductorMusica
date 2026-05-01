package demo.demo.controller;

import demo.demo.model.Song;
import demo.demo.repository.SongRepository;


import java.util.List;

/**
 * Controlador de lógica de negocio para el reproductor de música.
 * Sirve como intermediario entre la vista y el repositorio.
 */
public class MusicPlayerController {

    private final SongRepository songRepository = SongRepository.getInstance();

    /**
     * Solicita la adición de una canción al repositorio.
     * @param song Canción a añadir.
     * @return true si la operación fue exitosa.
     */
    public boolean addSong(Song song){
        return songRepository.addSong(song);
    }

    /**
     * Solicita la eliminación de una canción del repositorio.
     * @param song Canción a eliminar.
     * @return true si la operación fue exitosa.
     */
    public boolean removeSong(Song song){
        return songRepository.removeSong(song);
    }

    /**
     * Obtiene todas las canciones disponibles en el sistema.
     * @return Lista de canciones.
     */
    public List<Song> getSongs(){
        return songRepository.getSongs();
    }

    /**
     * Filtra una lista de canciones según un criterio de búsqueda (nombre, artista o álbum).
     * @param allSongs Lista original de canciones.
     * @param query Término de búsqueda.
     * @return Lista de canciones que coinciden con el filtro.
     */
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
