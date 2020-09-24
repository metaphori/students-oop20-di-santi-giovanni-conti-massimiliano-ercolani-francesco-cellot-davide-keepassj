package view.controllers;

import java.awt.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class AddEntryController {
    
    @FXML
    private TextField title;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField url;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void confirmAdd(ActionEvent event) {

    }
}
