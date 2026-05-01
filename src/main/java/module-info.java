module demo.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens demo.demo to javafx.fxml;
    opens demo.demo.viewController to javafx.fxml;

    exports demo.demo;
    exports demo.demo.viewController;
    exports demo.demo.model;
}