package view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import controller.DBDataSaver;
import controller.DBDataSaverImpl;
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
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

    private KDBHeader header = new KDBHeader();
    private String source = "/view/createnew/choosePassMenu.fxml";
    private FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    private FxmlSetter setter = new FxmlSetterImpl();
    private DBDataSaver data = new DBDataSaverImpl();

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

    /**
     * Takes the data from the previous controller.
     * @param data is the data passed by the previous fxml file controller
     */
    public void takeData(final DBDataSaver data) {
        this.data = data;
    }

    @FXML
    private void selectEA(final ActionEvent event) {
        String selection = comboEA.getSelectionModel().getSelectedItem().toString();
        this.algDescription.setText(header.getCipherDescriptions().get(selection));
        this.data.takeCipher(selection);
    }

    @FXML
    private void selectKDF(final ActionEvent event) {
        String selection = comboKDF.getSelectionModel().getSelectedItem().toString();

        this.kdfDescription.setText(header.getKDFDescriptions().get(selection));
        this.data.takeKdf(selection);
        this.setter.setSpinner(trSpinner, header.getKDFRounds(selection), header.getKDFRounds(selection));

        if(header.isKDFTweakable(selection)) {
            this.muSpinner.setDisable(false);
            this.pSpinner.setDisable(false);
            this.data.isTweakable(true);
            this.setter.setSpinner(muSpinner, header.getKDFMemory(), header.getKDFMaxMemory(selection));
            this.setter.setSpinner(pSpinner, header.getKDFParallelism(), header.getKDFMaxParallelism(selection));

        } else {
            this.muSpinner.setDisable(true);
            this.pSpinner.setDisable(true);
            this.data.isTweakable(false);
        }
    }

    @FXML
    private void cancelCreation(final ActionEvent event) {
        loader.getMainMenuScene();
        setter.getStage(event).close();
    }

    @FXML
    private void continueCreation(final ActionEvent event) {
        if (this.data.getCipher() == null) {
            setter.warningDialog("Choose an encryption algorithm");
        } else if (this.data.getKdf() == null) {
            setter.warningDialog("Choose a key derivation function");
            } else { 
                this.data.takeRounds(this.trSpinner.getValue());
                this.data.takeMemory(this.muSpinner.getValue());
                this.data.takeParallelism(this.pSpinner.getValue());
                this.loader.getSceneData(this.data, ChoosePassController.class);
                setter.getStage(event).close();
            }
    }
}
