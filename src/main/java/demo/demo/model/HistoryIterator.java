package demo.demo.model;

import java.util.Stack;

public class HistoryIterator implements SongIterator{

    private Stack<Song> songs;
    private int index = 0;

    public HistoryIterator(HistoryLog stackHistory) {
        this.songs = stackHistory.getHistory();  //Referenciar la pila para recorrerla
        this.index = songs.size() - 1;  //Empezar desde el final (el último en entrar)
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public Song getNext() {
        if(!hasNext()) return null;
        return songs.get(index--); //Mover el puntero hacia abjo en la pila
    }

    @Override
    public boolean hasPrevious() {
        return index < songs.size() - 2;
    }

    @Override
    public Song getPrevious() {
        index += 2;
        return songs.get(index--);
    }
}
