package demo.demo.model;

import java.util.List;

public class PlaylistIterator implements SongIterator{

    private Playlist playlist;
    private int index = 0;

    public PlaylistIterator(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean hasNext() {
        List<Song> list = playlist.getSongs();
        return index < list.size();
    }

    @Override
    public Song getNext() {
        if(!hasNext()) return null;

        Song song = playlist.getSongs().get(index);
        index++;
        return song;
    }

    @Override
    public boolean hasPrevious() {
        return index > 1;
    }

    @Override
    public Song getPrevious() {
        index -= 2;
        return playlist.getSongs().get(index++);
    }
}
