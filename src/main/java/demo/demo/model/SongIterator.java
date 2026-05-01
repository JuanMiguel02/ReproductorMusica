package demo.demo.model;

/**
 * Interfaz que define el comportamiento de un iterador para una colección de canciones.
 * Permite recorrer canciones hacia adelante y hacia atrás.
 */
public interface SongIterator {

    /**
     * Verifica si hay más canciones disponibles en la secuencia.
     * @return true si hay más canciones, false de lo contrario.
     */
    boolean hasNext();

    /**
     * Obtiene la siguiente canción y avanza la posición del iterador.
     * @return La siguiente canción en la secuencia, o null si no hay más.
     */
    Song getNext();

    /**
     * Verifica si hay canciones previas disponibles en la secuencia.
     * @return true si hay canciones previas, false de lo contrario.
     */
    boolean hasPrevious();

    /**
     * Obtiene la canción anterior y retrocede la posición del iterador.
     * @return La canción anterior en la secuencia.
     */
    Song getPrevious();

}
