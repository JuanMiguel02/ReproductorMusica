package demo.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Clase principal de la aplicación que inicia la interfaz gráfica del reproductor de música.
 * Gestiona la carga de la vista inicial y el comportamiento de la ventana (arrastrar, transparencia).
 */
public class App extends Application {
    private double x =0;
    private double y = 0;

    /**
     * Punto de entrada principal de la aplicación JavaFX.
     * Configura la escena, carga el archivo FXML y define eventos de ratón para la ventana.
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("music-player.fxml")));

        Scene scene = new Scene(root, 900,600);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();

        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased(event -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("Reproductor de Música");
        stage.setScene(scene);
        stage.show();
    }
}
