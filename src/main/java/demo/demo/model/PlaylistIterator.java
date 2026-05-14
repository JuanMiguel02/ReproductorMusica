package demo.demo.model;

/**
 * Iterador concreto para recorrer una {@link Playlist}.
 * Implementa el recorrido secuencial de canciones.
 */
public class PlaylistIterator implements SongIterator{

    private final Playlist playlist;
    private int index = 0;
    private boolean lastMoveWasNext = true;

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
        return index < playlist.getSongs().size();
    }

    /**
     * Obtiene la siguiente canción de la lista y avanza la posición del iterador.
     * <p>
     * Si el último movimiento realizado fue hacia atrás ({@code getPrevious()}),
     * se ajusta el índice para evitar repetir la misma canción al cambiar
     * de dirección en el recorrido.
     * </p>
     *
     * @return La siguiente canción de la playlist, o {@code null} si no existen más canciones.
     */
    @Override
    public Song getNext() {

        if(!hasNext()) return null;

        // Ajustar el índice si el último movimiento fue hacia atrás
        if(!lastMoveWasNext){
            index++;
        }

        Song song = playlist.getSongs().get(index);

        // Avanzar el índice para apuntar a la siguiente canción
        index++;

        lastMoveWasNext = true;

        return song;
    }

    /**
     * Comprueba si existe una canción antes de la posición actual.
     * @return true si hay una canción previa.
     */
    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    /**
     * Obtiene la canción anterior de la lista y retrocede la posición del iterador.
     * <p>
     * Si el último movimiento realizado fue hacia adelante ({@code getNext()}),
     * se ajusta el índice para evitar repetir la misma canción al cambiar
     * de dirección en el recorrido.
     * </p>
     *
     * @return La canción anterior de la playlist.
     */
    @Override
    public Song getPrevious() {

        if(!hasPrevious()) return null;

        // Ajustar el índice si el último movimiento fue hacia adelante
        if(lastMoveWasNext){
            index--;
        }
        // Retroceder a la canción anterior
        index--;

        Song song = playlist.getSongs().get(index);

        lastMoveWasNext = false;

        return song;
    }
}
