package view.controllers;

import controller.DBDataSaver;
import controller.DBDataSaverImpl;
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * Controller for chooseNameDb.fxml file.
 *
 */
public class ChooseNameDBController {

    private final String source = "/view/createnew/chooseEncryptionSet.fxml";
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl(source);
    private final DBDataSaver data = new DBDataSaverImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();
    Stage stage = new Stage();

    @FXML
    private TextField dbName;

    @FXML
    private TextField dbDescription;

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
        if (this.dbName.getText().trim().isBlank()) {
            setter.showDialog("Choose a database name", AlertType.ERROR);
        } else if (this.dbDescription.getText().trim().isBlank()) {
            setter.showDialog("Choose a database description", AlertType.ERROR);
        } else {
            this.data.takeDBName(this.dbName.getText().toString());
            this.data.takeDBDesc(this.dbDescription.getText().toString());
            this.loader.getSceneData(this.data, ChooseEncrSetController.class);
            this.setter.getStage(event).close();
        }
    }
}

