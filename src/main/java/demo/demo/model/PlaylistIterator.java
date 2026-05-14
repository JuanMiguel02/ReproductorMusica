package demo.demo.model;

import java.util.List;

/**
 * Iterador concreto para recorrer una {@link Playlist}.
 * Implementa el recorrido secuencial de canciones.
 */
public class PlaylistIterator implements SongIterator{

    private final Playlist playlist;
    private int index = 0;

    /**
     * Constructor del iterador para una lista de reproducción.
     * @param playlist La lista de reproducción a recorrer.
     */
    public PlaylistIterator(Playlist playlist) {
        this.playlist = playlist;
    }

    /**
     * Comprueba si existe una canción después de la posición actual.
     * @return true si hay una canción siguiente.
     */
    @Override
    public boolean hasNext() {
        List<Song> list = playlist.getSongs();
        return index < list.size();
    }

    /**
     * Obtiene la siguiente canción y avanza el índice.
     * @return La canción siguiente o null si no hay más.
     */
    @Override
    public Song getNext() {
        if(!hasNext()) return null;

        Song song = playlist.getSongs().get(index);
        index++;
        return song;
    }

    /**
     * Comprueba si existe una canción antes de la posición actual.
     * @return true si hay una canción previa.
     */
    @Override
    public boolean hasPrevious() {
        return index > 1;
    }

    /**
     * Obtiene la canción anterior y retrocede el índice.
     * @return La canción anterior.
     */
    @Override
    public Song getPrevious() {
        if(!hasPrevious()) return null;
        index -= 2;
        return playlist.getSongs().get(index++);
    }
}
