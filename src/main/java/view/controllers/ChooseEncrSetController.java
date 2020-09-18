package view.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import controller.DBDataSaver;
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import model.kdbx.KDBHeader;

public class ChooseEncrSetController implements Initializable {
    
    KDBHeader header = new KDBHeader();
    String source = "/resource/choosePassMenu.fxml";
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    FxmlSetter setter = new FxmlSetterImpl();
    DBDataSaver data;
    
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listEA = FXCollections.observableArrayList(this.header.getCipherDescriptions().keySet());
        ObservableList<String> listKDF = FXCollections.observableArrayList(this.header.getKDFDescriptions().keySet());
        
        this.algDescription.setWrapText(true);
        this.algDescription.setEditable(false);
        this.kdfDescription.setWrapText(true);
        this.kdfDescription.setEditable(false);

        this.comboEA.setItems(listEA);
        this.comboKDF.setItems(listKDF);        
    }
    
    public void takeData(DBDataSaver data) {
        this.data = data;
    }
    
    @FXML
    void selectEA(ActionEvent event) {
        String selection = comboEA.getSelectionModel().getSelectedItem().toString();
        algDescription.setText(header.getCipherDescriptions().get(selection));
        this.data.takeCipher(selection);
    }

    @FXML
    void selectKDF(ActionEvent event) {
        String selection = comboKDF.getSelectionModel().getSelectedItem().toString();
        kdfDescription.setText(header.getKDFDescriptions().get(selection));
        this.data.takeKdf(selection);
        
        this.setter.setSpinner(muSpinner, 1, header.getKDFMaxMemory(selection));
        this.setter.setSpinner(pSpinner, 1, header.getKDFMaxMemory(selection));
        this.setter.setSpinner(trSpinner, 1, header.getKDFRounds(selection));
        
        if(header.isKDFTweakable(selection) == false) {
            this.muSpinner.setDisable(true);
            this.pSpinner.setDisable(true);
        }
    }
    
    @FXML
    void cancelCreation(ActionEvent event) {

    }

    @FXML
    void continueCreation(ActionEvent event) {
        this.data.takeRounds(this.trSpinner.getValue());
        this.data.takeMemory(this.muSpinner.getValue());
        this.data.takeParallelism(this.pSpinner.getValue());
        
        this.loader.getSceneData(this.data, ChoosePassController.class);
    }

    @FXML
    void goBack(ActionEvent event) {

    }
    
}
