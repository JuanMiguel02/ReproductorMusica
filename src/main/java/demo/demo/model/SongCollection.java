package demo.demo.model;

/**
 * Interfaz que define una colección de canciones que puede ser recorrida mediante un iterador.
 * Parte del patrón de diseño Iterator.
 */
public interface SongCollection {
    /**
     * Crea y devuelve un iterador para recorrer la colección de canciones.
     * @return Una instancia de {@link SongIterator}.
     */
    SongIterator createIterator();
}
