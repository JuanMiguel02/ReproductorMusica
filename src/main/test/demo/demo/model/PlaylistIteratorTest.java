package demo.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el iterador de listas de reproducción ({@link PlaylistIterator}).
 * Verifica la navegación secuencial hacia adelante y hacia atrás.
 */
class PlaylistIteratorTest {

    private SongIterator iterator;

    /**
     * Configura el entorno de prueba con una lista de reproducción de ejemplo.
     */
    @BeforeEach
    void setUp() {
        Playlist playlist = new Playlist();
        playlist.addSong(new Song("Lhabia", "Deftones", "Around The Fur", Duration.ofMinutes(4)));
        playlist.addSong(new Song("Bliss", "Muse", "Origin of Symmetry", Duration.ofMinutes(4)));
        iterator = playlist.createIterator();
    }

    /**
     * Verifica el recorrido secuencial hacia adelante (FIFO).
     */
    @Test
    void testForwardNavigation(){
        assertTrue(iterator.hasNext());
        assertEquals("Lhabia", iterator.getNext().getName());

        assertTrue(iterator.hasNext());
        assertEquals("Bliss", iterator.getNext().getName());

        assertFalse(iterator.hasNext());
        assertNull(iterator.getNext());
    }

    /**
     * Verifica la capacidad de retroceder en la lista de reproducción.
     */
    @Test
    void testBackwardNavigation(){
        iterator.getNext();
        iterator.getNext();

        assertTrue(iterator.hasPrevious());
        assertEquals("Lhabia", iterator.getPrevious().getName());

        assertFalse(iterator.hasPrevious());
        assertEquals("Bliss", iterator.getNext().getName());
    }

    /**
     * Verifica el comportamiento con una lista de reproducción sin canciones.
     */
    @Test
    void testEmptyPlaylist(){
        Playlist emptyPlaylist = new Playlist();
        SongIterator emptyIterator = emptyPlaylist.createIterator();
        assertFalse(emptyIterator.hasNext());
        assertNull(emptyIterator.getNext());
    }
}