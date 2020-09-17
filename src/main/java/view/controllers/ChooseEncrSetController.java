package view.controllers;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class ChooseEncrSetController implements Initializable {
    
    @FXML
    private ComboBox comboEA;

    @FXML
    private ComboBox comboKDF;

    @FXML
    void selectEA(ActionEvent event) {

    }

    @FXML
    void selectKDF(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listEA = FXCollections.observableArrayList("AES 256-bit", "ChaCha20 256-bit");
        ObservableList<String> listKDF = FXCollections.observableArrayList();
        

        comboEA.setItems(listEA);
        
    }

}
