package demo.demo.model;

import java.util.Stack;

public class HistoryLog implements SongCollection{

    private Stack<Song> stackHistory = new Stack<>();

    public void registerSong(Song song) {
        stackHistory.push(song);
    }

    public Stack<Song> getHistory() {
        return stackHistory;
    }

    @Override
    public SongIterator createIterator() {
        return new HistoryIterator(this);
    }
}
