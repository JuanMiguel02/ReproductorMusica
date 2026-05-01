package demo.demo.services;

import javafx.scene.control.Alert;

/**
 * Servicio de utilidad para mostrar alertas en la interfaz gráfica de JavaFX.
 */
public class AlertService {

    /**
     * Muestra una alerta personalizada.
     * @param titulo Título de la ventana de alerta.
     * @param mensaje Contenido del mensaje.
     * @param tipo Tipo de alerta (INFORMATION, WARNING, ERROR, etc.).
     */
    public static void showAlert(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta de error simplificada.
     * @param mensaje Mensaje de error a mostrar.
     */
    public static void showErrorAlert(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("ERROR");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
