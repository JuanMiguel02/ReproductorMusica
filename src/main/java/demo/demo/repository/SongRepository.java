package demo.demo.repository;

import demo.demo.model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.util.List;

/**
 * Repositorio centralizado para gestionar la persistencia en memoria de las canciones.
 * Implementa el patrón Singleton para asegurar una única fuente de datos.
 */
public class SongRepository {

    private final ObservableList<Song> songs = FXCollections.observableArrayList();
    private static SongRepository instance;

    private SongRepository(){
        loadSongsExampe();
    }

    /**
     * Obtiene la instancia única del repositorio.
     * @return Instancia de {@link SongRepository}.
     */
    public static SongRepository getInstance(){
        if(instance == null){
            instance = new SongRepository();
        }
        return instance;
    }

    /**
     * Añade una nueva canción al repositorio.
     * @param song La canción a añadir.
     * @return true si se añadió correctamente.
     */
    public boolean addSong(Song song){
        return songs.add(song);
    }

    /**
     * Obtiene la lista de todas las canciones disponibles.
     * @return Una lista inmutable/observable de canciones.
     */
    public List<Song> getSongs(){
        return songs;
    }

    /**
     * Elimina una canción del repositorio.
     * @param song La canción a eliminar.
     * @return true si se eliminó correctamente.
     */
    public boolean removeSong(Song song){
        return songs.remove(song);
    }

    /**
     * Carga canciones de ejemplo al inicializar el repositorio.
     */
    private void loadSongsExampe(){
        Song song1 = new Song("Sextape", "Deftones", "Diamond Eyes", Duration.ofMinutes(4));
        song1.setFilePath("src/main/resources/music/Sextape.mp3");

        Song song2 = new Song("The Adults Are Talking", "The Strokes", "The New Abnormal", Duration.ofMinutes(5));
        song2.setFilePath("src/main/resources/music/TheAdultsAreTalking.mp3");

        Song song3 = new Song("Idilio", "Willie Colón", "Mis Favoritas", Duration.ofMinutes(5));
        song3.setFilePath("src/main/resources/music/Idilio.mp3");

        addSong(song1);
        addSong(song2);
        addSong(song3);
    }

}
