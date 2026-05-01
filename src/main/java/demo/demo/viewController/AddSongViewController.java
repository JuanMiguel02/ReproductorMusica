package demo.demo.viewController;

import demo.demo.controller.MusicPlayerController;
import demo.demo.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.Duration;

import static demo.demo.services.AlertService.showAlert;
import static demo.demo.services.AlertService.showErrorAlert;

/**
 * Controlador de la vista para añadir una nueva canción.
 * Gestiona el formulario y la selección de archivos de audio.
 */
public class AddSongViewController {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtAlbum;
    @FXML
    private TextField txtFilePath;

    private final MusicPlayerController musicPlayerController = new MusicPlayerController();
    private double detectedSeconds = 0;

    /**
     * Se ejecuta al pulsar el botón "Guardar".
     * Valida los campos y añade la canción al sistema.
     */
    @FXML
    private void onSave() {
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String album = txtAlbum.getText();
        String filePath = txtFilePath.getText();

        if (title.isEmpty() || artist.isEmpty() || album.isEmpty() || filePath.isEmpty()) {
            showErrorAlert( "Todos los campos son obligatorios, incluyendo el archivo.");
            return;
        }

        try {
            Song newSong = new Song(title, artist, album, Duration.ofSeconds((long)detectedSeconds));
            newSong.setFilePath(filePath);

            if(musicPlayerController.addSong(newSong)){
                showAlert("Éxito", "Canción añadida correctamente.", Alert.AlertType.INFORMATION);
                closeWindow();
            }else{
                showErrorAlert("No se pudo añadir la canción.");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Se ejecuta al pulsar el botón "Seleccionar Archivo".
     * Abre un selector de archivos y detecta la duración del audio seleccionado.
     */
    @FXML
    private void onSelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de audio");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Audio", "*.mp3", "*.wav"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(txtTitle.getScene().getWindow());

        if (selectedFile != null) {

            Media media = new Media(selectedFile.toURI().toString());
            MediaPlayer tempPlayer = new MediaPlayer(media);
            tempPlayer.setOnReady(() ->{

                this.detectedSeconds = media.getDuration().toSeconds();
                String durationStr = formatTime(detectedSeconds);
                System.out.println("Duración detectada: " + durationStr + " segundos");

                tempPlayer.dispose();

            });

            txtFilePath.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Se ejecuta al pulsar el botón "Cancelar".
     * Cierra la ventana sin guardar cambios.
     */
    @FXML
    void onCancel() {
        closeWindow();
    }

    /**
     * Formatea un valor en segundos a una cadena con formato mm:ss.
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

    /**
     * Cierra la ventana actual.
     */
    private void closeWindow() {
        Stage stage = (Stage) txtTitle.getScene().getWindow();
        stage.close();
    }
}
