package view.controllers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class OpenDatabaseController {
    @FXML
    private PasswordField password;
    File file;

    public void takeFile(File file) {
        this.file = file;
    }
    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void openDatabase(ActionEvent event) {

    }
}
