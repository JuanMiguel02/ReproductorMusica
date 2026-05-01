package demo.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Clase que representa una lista de reproducción de canciones.
 * Implementa {@link SongCollection} para permitir la creación de un iterador.
 */
public class Playlist implements SongCollection{

    private final ObservableList<Song> songs = FXCollections.observableArrayList();

    /**
     * Añade una canción a la lista de reproducción.
     * @param song La canción a añadir.
     */
    public void addSong(Song song) {
        songs.add(song);
    }

    /**
     * Obtiene la lista subyacente de canciones.
     * @return Una lista de objetos {@link Song}.
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Crea un iterador específico para recorrer esta lista de reproducción.
     * @return Una instancia de {@link PlaylistIterator}.
     */
    @Override
    public SongIterator createIterator() {
        return new PlaylistIterator(this);
    }
}
