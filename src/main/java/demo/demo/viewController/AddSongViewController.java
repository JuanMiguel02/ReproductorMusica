package demo.demo.viewController;

import demo.demo.controller.MusicPlayerController;
import demo.demo.model.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.Duration;

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

    @FXML
    private void onSave() {
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String album = txtAlbum.getText();
        String filePath = txtFilePath.getText();

        if (title.isEmpty() || artist.isEmpty() || album.isEmpty() || filePath.isEmpty()) {
            showAlert("Error", "Todos los campos son obligatorios, incluyendo el archivo.");
            return;
        }

        try {
            Song newSong = new Song(title, artist, album, Duration.ofSeconds((long)detectedSeconds));
            newSong.setFilePath(filePath);
            boolean success = musicPlayerController.addSong(newSong);
            
            if (success) {
                showAlert("Éxito", "Canción añadida correctamente.");
                closeWindow();
            } else {
                showAlert("Error", "No se pudo añadir la canción.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "La duración debe ser un número (segundos).");
        }
    }

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

    @FXML
    void onCancel() {
        closeWindow();
    }

    private String formatTime(double totalSeconds){
        if(Double.isNaN(totalSeconds) || totalSeconds < 0){
            return "00:00";
        }
        int minutes = (int) (totalSeconds / 60);
        int seconds = (int) (totalSeconds % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtTitle.getScene().getWindow();
        stage.close();
    }
}
