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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import model.kdbx.KDBHeader;

/**
 * 
 * Controller for chooseEncryptionSet.fxml file.
 *
 */
public class ChooseEncrSetController implements Initializable {

    private final KDBHeader header = new KDBHeader();
    private final String source = "/view/createnew/choosePassMenu.fxml";
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    private final FxmlSetter setter = new FxmlSetterImpl();
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
    public final void initialize(final URL location, final ResourceBundle resources) {
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
        final String selection = comboEA.getSelectionModel().getSelectedItem().toString();
        this.algDescription.setText(header.getCipherDescriptions().get(selection));
        this.data.takeCipher(selection);
    }

    @FXML
    private void selectKDF(final ActionEvent event) {
        final String selection = comboKDF.getSelectionModel().getSelectedItem().toString();

        this.kdfDescription.setText(header.getKDFDescriptions().get(selection));
        this.data.takeKdf(selection);
        this.setter.setSpinner(trSpinner, header.getKDFRounds(selection), header.getKDFRounds(selection));

        if (header.isKDFTweakable(selection)) {
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
        if (setter.showDialog("Are you sure you want to abort the creation?\n"
                + "Data will be lost.", AlertType.CONFIRMATION)) {
            loader.getMainMenuScene();
            setter.getStage(event).close();
        }
    }

    @FXML
    private void continueCreation(final ActionEvent event) {
        if (this.data.getCipher() == null) {
            setter.showDialog("Choose an encryption algorithm", AlertType.ERROR);
        } else if (this.data.getKdf() == null) {
            setter.showDialog("Choose a key derivation function", AlertType.ERROR);
            } else { 
                this.data.takeRounds(this.trSpinner.getValue());
                this.data.takeMemory(this.muSpinner.getValue());
                this.data.takeParallelism(this.pSpinner.getValue());
                this.loader.getSceneData(this.data, ChoosePassController.class);
                setter.getStage(event).close();
            }
    }
}
