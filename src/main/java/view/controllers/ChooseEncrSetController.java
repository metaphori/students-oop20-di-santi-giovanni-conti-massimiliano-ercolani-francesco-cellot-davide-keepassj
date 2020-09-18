package view.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import model.kdbx.KDBHeader;

public class ChooseEncrSetController implements Initializable {
    
    KDBHeader header = new KDBHeader();
    
    @FXML
    private ComboBox<String> comboEA;

    @FXML
    private ComboBox<String> comboKDF;
    
    @FXML
    private TextArea algDescription;
   
    @FXML
    private TextArea kdfDescription;

    @FXML
    private Spinner<Integer> trSpinner;

    @FXML
    private Spinner<Integer> muSpinner;

    @FXML
    private Spinner<Integer> pSpinner;

    

    @FXML
    void selectEA(ActionEvent event) {
        String selection = comboEA.getSelectionModel().getSelectedItem().toString();
        algDescription.setText(header.getCipherDescriptions().get(selection));
    }

    @FXML
    void selectKDF(ActionEvent event) {
        String selection = comboKDF.getSelectionModel().getSelectedItem().toString();
        kdfDescription.setText(header.getKDFDescriptions().get(selection));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listEA = FXCollections.observableArrayList(header.getCipherDescriptions().keySet());
        ObservableList<String> listKDF = FXCollections.observableArrayList(header.getKDFDescriptions().keySet());
        
        algDescription.setWrapText(true);
        algDescription.setEditable(false);
        kdfDescription.setWrapText(true);
        kdfDescription.setEditable(false);

        comboEA.setItems(listEA);
        comboKDF.setItems(listKDF);
        
    }

}
