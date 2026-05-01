package demo.demo.model;

import java.util.Stack;

/**
 * Clase que gestiona el registro de canciones reproducidas recientemente.
 * Utiliza una pila (Stack) para mantener el orden del historial.
 */
public class HistoryLog implements SongCollection{

    private Stack<Song> stackHistory = new Stack<>();

    /**
     * Registra una canción en el historial si no es igual a la última registrada.
     * @param song Canción a registrar.
     */
    public void registerSong(Song song) {
        if(!stackHistory.isEmpty() && stackHistory.peek().equals(song)){
            return;
        }
        stackHistory.push(song);
    }

    /**
     * Obtiene la pila del historial de canciones.
     * @return Stack con las canciones reproducidas.
     */
    public Stack<Song> getHistory() {
        return stackHistory;
    }

    /**
     * Crea un iterador para recorrer el historial de canciones.
     * @return Una instancia de {@link HistoryIterator}.
     */
    @Override
    public SongIterator createIterator() {
        return new HistoryIterator(this);
    }
}
