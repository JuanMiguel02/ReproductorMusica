package demo.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el iterador del historial de canciones ({@link HistoryIterator}).
 * Verifica el orden de navegación (LIFO) y el comportamiento con historiales vacíos.
 */
class HistoryIteratorTest {

    private HistoryLog historyLog;
    private SongIterator iterator;

    /**
     * Configura el entorno de prueba antes de cada ejecución.
     * Registra algunas canciones iniciales en el historial.
     */
    @BeforeEach
    void setUp() {
        historyLog = new HistoryLog();
        historyLog.registerSong(new Song("One For The Road", "Arctic Monkeys", "AM", Duration.ofMinutes(3)));
        historyLog.registerSong(new Song("Reptillia", "The Strokes", "Room On Fire", Duration.ofMinutes(4)));
        iterator = historyLog.createIterator();
    }

    /**
     * Verifica que el historial se recorra desde la canción más reciente a la más antigua.
     */
    @Test
    void testHistoryOrder(){
        assertTrue(iterator.hasNext());
        assertEquals("Reptillia", iterator.getNext().getName(), "La primera canción del historial debe ser la última registrada");

        assertTrue(iterator.hasNext());
        assertEquals("One For The Road", iterator.getNext().getName());

        assertFalse(iterator.hasNext());

    }

    /**
     * Verifica la capacidad de retroceder (moverse hacia canciones más nuevas) en el iterador de historial.
     */
    @Test
    void testHistoryPrevious(){
        historyLog.registerSong(new Song("The Spell of Mathematics", "Deftones", "Ohms", Duration.ofMinutes(5)));

        iterator.getNext();
        iterator.getNext();
        assertTrue(iterator.hasPrevious());
        assertEquals("Reptillia", iterator.getPrevious().getName());
    }

    /**
     * Verifica que un historial vacío se comporte correctamente (sin elementos que iterar).
     */
    @Test
    void testEmptyHistory(){
        HistoryLog emptyLog = new HistoryLog();
        SongIterator emptyIterator = emptyLog.createIterator();
        assertFalse(emptyIterator.hasNext());
        assertNull(emptyIterator.getNext());
    }
}