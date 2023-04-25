module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires javafx.web;
    requires jdk.jsobject;

    opens org.example to javafx.graphics, javafx.base;
    exports org.example;
    exports org.example.ui.controllers;
    opens org.example.ui.controllers to javafx.base, javafx.graphics, javafx.fxml;
    exports org.example.network;
    opens org.example.network to javafx.base, javafx.graphics;
    exports org.example.model;
    opens org.example.model to javafx.base, javafx.graphics;
    exports org.example.ui.view;
    opens org.example.ui.view to javafx.base, javafx.graphics;
}