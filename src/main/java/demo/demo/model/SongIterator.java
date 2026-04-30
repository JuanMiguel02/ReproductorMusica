package demo.demo.model;

public interface SongIterator {

    boolean hasNext(); //Pregunta si hay más canciones
    Song getNext(); //Da la siguiente canción y avanza

    boolean hasPrevious();
    Song getPrevious();

}
