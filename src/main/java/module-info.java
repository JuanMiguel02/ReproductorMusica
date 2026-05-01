module demo.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.fontawesome6;

    opens demo.demo to javafx.fxml;
    opens demo.demo.viewController to javafx.fxml;

    exports demo.demo;
    exports demo.demo.viewController;
    exports demo.demo.model;

}