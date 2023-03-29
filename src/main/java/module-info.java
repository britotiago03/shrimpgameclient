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
    exports org.example.userinterface;
    opens org.example.userinterface to javafx.base, javafx.graphics;
    exports org.example.controllers;
    opens org.example.controllers to javafx.base, javafx.graphics;
    exports org.example.network;
    opens org.example.network to javafx.base, javafx.graphics;
}