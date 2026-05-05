package demo.demo.model;

/**
 * Iterador concreto para recorrer el historial de canciones ({@link HistoryLog}).
 * El recorrido se realiza desde la canción más reciente a la más antigua.
 */
public class HistoryIterator implements SongIterator{

    private HistoryLog historyLog;
    private int index;

    /**
     * Constructor del iterador de historial.
     * Inicializa el índice al final de la pila (la canción más reciente).
     * @param historyLog El log de historial a recorrer.
     */
    public HistoryIterator(HistoryLog historyLog) {
        this.historyLog = historyLog;
        index = historyLog.getHistory().size() - 1; //Empezar desde el final (el último en entrar)
    }

    /**
     * Comprueba si hay más canciones en el historial (hacia atrás en el tiempo).
     * @return true si quedan canciones por recorrer.
     */
    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    /**
     * Obtiene la siguiente canción (cronológicamente anterior) y mueve el puntero.
     * @return La canción siguiente en el recorrido del historial.
     */
    @Override
    public Song getNext() {
        if(!hasNext()) return null;
        return historyLog.getHistory().get(index--); //Mover el puntero hacia abjo en la pila
    }

    /**
     * Comprueba si se puede retroceder en el recorrido del historial (hacia canciones más nuevas).
     * @return true si se puede volver a una canción anterior en el recorrido.
     */
    @Override
    public boolean hasPrevious() {
        return index < historyLog.getHistory().size() - 2;
    }

    /**
     * Obtiene la canción anterior en el recorrido y ajusta el índice.
     * @return La canción "previa" (más reciente respecto al puntero actual).
     */
    @Override
    public Song getPrevious() {
        if(!hasPrevious()) return null;
        index += 2;
        return historyLog.getHistory().get(index--);
    }
}
