package demo.demo.viewController;

import demo.demo.model.HistoryLog;
import demo.demo.model.Playlist;
import demo.demo.model.Song;
import demo.demo.model.SongIterator;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.time.Duration;
import java.util.Locale;

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

    private Playlist playlist;
    private HistoryLog historyLog;

    private SongIterator songIterator;
    private Song currentSong;

    private MediaPlayer mediaPlayer;


    public void initialize() {
        playlist = new Playlist();
        historyLog = new HistoryLog();

        Song song1 = new Song("Song 1", "Artist 1", "Album 1", Duration.ofMinutes(3));
        song1.setFilePath("src/main/resources/music/TheAdultsAreTalking.mp3");

        Song song2 = new Song("Song 2", "Artist 2", "Album 2", Duration.ofMinutes(3));
        song2.setFilePath("src/main/resources/music/BeautySchool.mp3");

        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(new Song("Song 3", "Artist 3", "Album 3", Duration.ofMinutes(3)));

        songIterator = playlist.createIterator();
        if (songIterator.hasNext()) {
            currentSong = songIterator.getNext();
        }

        initializeTable();
        loadTable();

        if (currentSong != null) {
            updatePlayerLabels("Listo");
        }
    }

    private void initializeTable(){
        colArtist.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getArtist()));
        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colAlbum.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAlbum()));
        colDuration.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDuration().toString()));
    }

    private void loadTable(){
        songTable.getItems().clear();
        songTable.getItems().addAll(playlist.getSongs());
    }

    @FXML
    private void playSelectedSong(){
        if(currentSong != null){
            if(mediaPlayer != null){
                mediaPlayer.stop();
            }
            try{
                File file = new File(currentSong.getFilePath());
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);

                updateProgressBar();

                mediaPlayer.play();

                mediaPlayer.setOnEndOfMedia(this::nextSong);
            } catch (Exception e) {
                System.err.println("No se pudo reproducir: " + currentSong.getFilePath());
            }
        }
    }

    @FXML
    private void nextSong() {
        if(songIterator.hasNext()){
            if(currentSong != null){
                historyLog.registerSong(currentSong);
            }
            currentSong = songIterator.getNext();
            playSelectedSong();
            updatePlayerLabels("Reproduciendo siguiente");
            refreshHistoryUI();
        }else{
            lblState.setText("Fin de la lista");
            System.out.println("Fin de la lista");
        }

    }

    @FXML
    private void previousSong() {
       if(songIterator.hasPrevious()){
           if(currentSong != null){
               historyLog.registerSong(currentSong);
           }
           currentSong = songIterator.getPrevious();
           playSelectedSong();
           updatePlayerLabels("Reproduciendo anterior");
           refreshHistoryUI();
       }else{
           lblState.setText("No hay canciones anteriores");
       }
    }
    
    private void refreshHistoryUI(){
        listHistory.getItems().clear();

        SongIterator it = historyLog.createIterator();

        while(it.hasNext()){
            Song song = it.getNext();
            listHistory.getItems().add(song.toString());
        }
    }


    private void updatePlayerLabels(String state){

        if (lblState != null) lblState.setText(state);
        if (currentSong != null) {
            if (lblCurrentSong != null) lblCurrentSong.setText("Reproduciendo: " + currentSong.toString());
            System.out.println(state + ": " + currentSong.toString());
        }
    }

    private void updateProgressBar(){
      mediaPlayer.setOnReady(() ->{
          sdProgress.setMax(mediaPlayer.getTotalDuration().toSeconds());
      });

      mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
          double total = mediaPlayer.getTotalDuration().toSeconds();
          double current = newValue.toSeconds();

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

    @FXML
    public void close(){
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) centralContent.getScene().getWindow();
        stage.setIconified(true);

    }
}
