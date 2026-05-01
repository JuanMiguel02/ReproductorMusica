package demo.demo.viewController;

import demo.demo.controller.MusicPlayerController;
import demo.demo.model.HistoryLog;
import demo.demo.model.Playlist;
import demo.demo.model.Song;
import demo.demo.model.SongIterator;
import demo.demo.repository.SongRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static demo.demo.services.AlertService.showAlert;
import static demo.demo.services.AlertService.showErrorAlert;

/**
 * Controlador principal de la vista del reproductor de música.
 * Gestiona la reproducción, lista de canciones, historial y controles de usuario.
 */
public class MusicPlayerViewController {

    @FXML
    private Label lblState;

    @FXML
    private Label lblCurrentSong;

    @FXML
    private TableView<Song> songTable;

    @FXML
    private TableColumn<Song, String> colTitle;

    @FXML
    private TableColumn<Song, String> colArtist;

    @FXML
    private TableColumn<Song, String> colAlbum;

    @FXML
    private TableColumn<Song, String> colDuration;
    
    @FXML
    private ListView<String> listHistory;

    @FXML
    private Slider sdProgress;

    @FXML
    private AnchorPane centralContent;

    @FXML
    private FontIcon iconButtonPlay;

    @FXML
    private TextField txtFilter;

    @FXML
    private Label lblTime;

    private Playlist playlist;
    private HistoryLog historyLog;

    private SongIterator songIterator;
    private Song currentSong;

    private MediaPlayer mediaPlayer;
    private final MusicPlayerController musicPlayerController = new MusicPlayerController();


    /**
     * Inicializa el controlador, configura la lista de canciones, el historial
     * y los listeners de la interfaz.
     */
    public void initialize() {
        playlist = new Playlist();
        historyLog = new HistoryLog();

        // Cargar canciones desde el repositorio
        playlist.getSongs().addAll(SongRepository.getInstance().getSongs());

        songIterator = playlist.createIterator();

        if (songIterator.hasNext()) {
            currentSong = songIterator.getNext();
        }

        initializeTable();
        loadTable();

        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Song> filteredSongs = musicPlayerController.filterSongs(playlist.getSongs(), newValue);
            songTable.getItems().clear();
            songTable.getItems().addAll(filteredSongs);

            Playlist temporaryPlaylist = new Playlist();
            temporaryPlaylist.getSongs().addAll(filteredSongs);
            songIterator = temporaryPlaylist.createIterator();

            if(songIterator.hasNext()){
                currentSong = songIterator.getNext();
            }
        });

        if (currentSong != null) {
            updatePlayerLabels("Listo");
        }
    }

    /**
     * Configura las columnas de la tabla de canciones.
     */
    private void initializeTable(){
        colArtist.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getArtist()));
        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colAlbum.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAlbum()));
        colDuration.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDurationFormatted()));
    }

    /**
     * Carga las canciones del repositorio en la tabla y en la lista de reproducción.
     */
    private void loadTable(){
        songTable.getItems().clear();
        // Asegurarnos de que playlist tenga lo último del repositorio
        playlist.getSongs().clear();
        playlist.getSongs().addAll(SongRepository.getInstance().getSongs());
        songTable.getItems().addAll(playlist.getSongs());
    }

    /**
     * Abre la ventana para añadir una nueva canción.
     */
    @FXML
    private void addSong() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/demo/demo/add-song.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Añadir Canción");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            // Recargar la tabla después de cerrar el formulario
            loadTable();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gestiona la acción de reproducir o pausar la canción actual.
     */
    @FXML
    private void handlePlayPause(){
        if(mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            pauseSong();
            iconButtonPlay.setIconCode(AntDesignIconsOutlined.PLAY_CIRCLE);
        }else{
            playSelectedSong();
            iconButtonPlay.setIconCode(AntDesignIconsOutlined.PAUSE_CIRCLE);
        }
    }

    /**
     * Reproduce la canción seleccionada actualmente.
     * Crea un nuevo {@link MediaPlayer} si es necesario.
     */
    private void playSelectedSong(){
       if (currentSong == null) return;

        //Preguntar si existe el reproductor y es la misma canción
       if(mediaPlayer != null && mediaPlayer.getMedia().getSource().equals(new File(currentSong.getFilePath()).toURI().toString())){
           //Si estaba pausado, play() lo reanuda desde donde estaba
           if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
               mediaPlayer.play();
               updatePlayerLabels("Reanudando");
           }
           return; //Se sale del método para no crear un reproductor nuevo
       }

       //Si es una canción diferente o el reproductor no existe, entonces se crea un nuevo reproductor
       if(mediaPlayer != null){
           mediaPlayer.stop();
           mediaPlayer.dispose();
       }
       try{
           //Crear el reproductor a partir del archivo de la canción
           File file = new File(currentSong.getFilePath());
           Media media = new Media(file.toURI().toString());
           mediaPlayer = new MediaPlayer(media);
           //Método para actualizar el slider
           updateProgressBar();

           mediaPlayer.play();
           //reproducir la siguiente canción cuando termine
           mediaPlayer.setOnEndOfMedia(this::nextSong);

           updatePlayerLabels("Reproduciendo nueva cancuón");
       } catch (Exception e) {
           System.err.println("No se pudo reproducir" + e.getMessage());
       }
    }

    /**
     * Pausa la reproducción actual y actualiza el estado de la interfaz.
     */
    private void pauseSong(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
        if(sdProgress != null){
            sdProgress.setValue(mediaPlayer != null ? mediaPlayer.getCurrentTime().toSeconds() : 0);
        }
        updatePlayerLabels("Pausado");
    }

    /**
     * Avanza a la siguiente canción en la lista de reproducción.
     * Registra la canción actual en el historial.
     */
    @FXML
    private void nextSong() {
        if(songIterator.hasNext()){
            if(currentSong != null){
                historyLog.registerSong(currentSong);
            }
            currentSong = songIterator.getNext();
            playSelectedSong();
            iconButtonPlay.setIconCode(AntDesignIconsOutlined.PAUSE_CIRCLE);
            updatePlayerLabels("Reproduciendo siguiente");
            refreshHistoryUI();
        }else{
            lblState.setText("Fin de la lista");
            System.out.println("Fin de la lista");
        }

    }

    /**
     * Retrocede a la canción anterior utilizando el iterador.
     */
    @FXML
    private void previousSong() {
       if(songIterator.hasPrevious()){
           if(currentSong != null){
               historyLog.registerSong(currentSong);
           }
           currentSong = songIterator.getPrevious();
           playSelectedSong();
           iconButtonPlay.setIconCode(AntDesignIconsOutlined.PAUSE_CIRCLE);
           updatePlayerLabels("Reproduciendo anterior");
           refreshHistoryUI();
       }else{
           lblState.setText("No hay canciones anteriores");
       }
    }
    
    /**
     * Actualiza la lista visual del historial de reproducción.
     */
    private void refreshHistoryUI(){
        listHistory.getItems().clear();

        SongIterator it = historyLog.createIterator();

        while(it.hasNext()){
            Song song = it.getNext();
            listHistory.getItems().add(song.toString());
        }
    }


    /**
     * Actualiza las etiquetas de texto de la interfaz con el estado actual.
     * @param state Estado actual de la reproducción.
     */
    private void updatePlayerLabels(String state){

        if (lblState != null) lblState.setText(state);
        if (currentSong != null) {
            if (lblCurrentSong != null) lblCurrentSong.setText("Reproduciendo: " + currentSong);
            System.out.println(state + ": " + currentSong.toString());
        }
    }

    /**
     * Configura los listeners para sincronizar la barra de progreso con el audio.
     */
    private void updateProgressBar(){
      mediaPlayer.setOnReady(() -> sdProgress.setMax(mediaPlayer.getTotalDuration().toSeconds()));

      mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
          double total = mediaPlayer.getTotalDuration().toSeconds();
          double current = newValue.toSeconds();

          if(lblTime != null){
              lblTime.setText(formatTime(current));
          }

          if(total > 0){
              double percentage = (current / total) * 100;

              String style = String.format(Locale.US,
                      "-fx-background-color: linear-gradient(to right, #1DB954 %f%%, #404040 %f%%);",
                      percentage, percentage
              );

              var track = sdProgress.lookup(".track");
              if(track != null){
                  track.setStyle(style);
              }

              if(!sdProgress.isValueChanging()){
                  sdProgress.setValue(current);
              }
          }

       });

      sdProgress.valueProperty().addListener((observable, oldValue, newValue) -> {
          if(sdProgress.isValueChanging()){
              mediaPlayer.seek(javafx.util.Duration.seconds(newValue.doubleValue()));
          }
      });

    }

    /**
     * Elimina la canción seleccionada en la tabla tras confirmación del usuario.
     */
    @FXML
    private void deleteSong() {
        Song selectedSong = songTable.getSelectionModel().getSelectedItem();

        if(selectedSong == null){
            showErrorAlert("Por favor seleccione un recinto para eliminar");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar eliminación");
        confirmation.setHeaderText("¿Está seguro que desea eliminar este recinto?");
        confirmation.setContentText("Recinto: " + selectedSong.getName() + " - " + selectedSong.getArtist());

        confirmation.showAndWait().ifPresent(response ->{
            if(response == ButtonType.OK){
                if(musicPlayerController.removeSong(selectedSong)){
                    loadTable();
                    showAlert("Éxito", "Recinto Eliminado Éxitosamente", Alert.AlertType.INFORMATION  );
                }

            }
        });
    }

    /**
     * Cierra la aplicación.
     */
    @FXML
    public void close(){
        System.exit(0);
    }

    /**
     * Minimiza la ventana de la aplicación.
     */
    @FXML
    public void minimize() {
        Stage stage = (Stage) centralContent.getScene().getWindow();
        stage.setIconified(true);

    }

    /**
     * Formatea segundos a una cadena con formato mm:ss.
     * @param totalSeconds Segundos totales.
     * @return Cadena formateada.
     */
    private String formatTime(double totalSeconds){
        if(Double.isNaN(totalSeconds) || totalSeconds < 0){
            return "00:00";
        }
        int minutes = (int) (totalSeconds / 60);
        int seconds = (int) (totalSeconds % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
