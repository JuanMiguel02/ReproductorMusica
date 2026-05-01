package demo.demo.model;

import java.time.Duration;

/**
 * Representa una canción en el sistema con sus metadatos básicos.
 */
public class Song {

    private String name;
    private String artist;
    private String album;
    private Duration duration;
    private String filePath;

    /**
     * Constructor para crear una nueva canción.
     * @param name Nombre de la canción.
     * @param artist Artista o banda de la canción.
     * @param album Álbum al que pertenece la canción.
     * @param duration Duración de la canción.
     */
    public Song(String name, String artist, String album, Duration duration) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    /**
     * Obtiene la ruta del archivo de audio en el disco.
     * @return Ruta absoluta o relativa del archivo.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Establece la ruta del archivo de audio.
     * @param filePath Nueva ruta del archivo.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Obtiene el nombre de la canción.
     * @return Nombre de la canción.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el artista de la canción.
     * @return Nombre del artista.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Obtiene el álbum de la canción.
     * @return Nombre del álbum.
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Establece el nombre de la canción.
     * @param name Nuevo nombre.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece el artista de la canción.
     * @param artist Nuevo artista.
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Establece el álbum de la canción.
     * @param album Nuevo álbum.
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Obtiene la duración de la canción.
     * @return Objeto {@link Duration} con la duración.
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Establece la duración de la canción.
     * @param duration Nueva duración.
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Devuelve la duración de la canción formateada como cadena (mm:ss).
     * @return Cadena formateada de la duración.
     */
    public String getDurationFormatted(){
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return name + " - " + artist + " - " + album;
    }

}
