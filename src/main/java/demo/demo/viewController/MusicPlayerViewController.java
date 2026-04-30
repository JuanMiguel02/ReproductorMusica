package demo.demo.viewController;

import demo.demo.model.HistoryLog;
import demo.demo.model.Playlist;
import demo.demo.model.Song;
import demo.demo.model.SongIterator;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.Duration;
import java.util.Stack;

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
    private TableColumn<Song, String> colDuration;

    private Playlist playlist;
    private HistoryLog historyLog;

    private SongIterator songIterator;
    private Song currentSong;

    public void initialize() {
        playlist = new Playlist();
        historyLog = new HistoryLog();

        playlist.addSong(new Song("Song 1", "Artist 1", "Album 1", Duration.ofMinutes(3)));
        playlist.addSong(new Song("Song 2", "Artist 2", "Album 2", Duration.ofMinutes(3)));
        playlist.addSong(new Song("Song 3", "Artist 3", "Album 3", Duration.ofMinutes(3)));

        songIterator = playlist.createIterator();
        if (songIterator.hasNext()) {
            currentSong = songIterator.getNext();
        }

        initializeTable();
        loadTable();

        if (currentSong != null) {
            updateInterface("Listo");
        }
    }

    private void initializeTable(){
        colArtist.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getArtist()));
        colTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colDuration.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDuration().toString()));
    }

    private void loadTable(){
        songTable.getItems().clear();
        songTable.getItems().addAll(playlist.getSongs());
    }

    @FXML
    private void nextSong() {
        if(songIterator.hasNext()){
            if(currentSong != null){
                historyLog.registerSong(currentSong);
            }
            currentSong = songIterator.getNext();
            updateInterface("Reproduciendo siguiente");
        }else{
            lblState.setText("Fin de la lista");
            System.out.println("Fin de la lista");
        }

    }

    @FXML
    private void previousSong() {
       if(songIterator.hasPrevious()){
           currentSong = songIterator.getPrevious();
           updateInterface("Reproduciendo anterior");
       }else{
           lblState.setText("No hay canciones anteriores");
       }
    }

    @FXML
    private void updateInterface(String state){
        if (lblState != null) lblState.setText(state);
        if (currentSong != null) {
            if (lblCurrentSong != null) lblCurrentSong.setText("Reproduciendo: " + currentSong.toString());
            System.out.println(state + ": " + currentSong.toString());
        }
    }

}
