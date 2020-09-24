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

/**
 * 
 * Controller for chooseNameDb.fxml file.
 *
 */
public class ChooseNameDBController {

    private final String source = "/view/createnew/chooseEncryptionSet.fxml";
    private FxmlFilesLoader loader = new FxmlFilesLoaderImpl(source);
    private DBDataSaver data = new DBDataSaverImpl();
    private FxmlSetter setter = new FxmlSetterImpl();

    @FXML
    private TextField dbName;

    @FXML
    private TextField dbDescription;

    @FXML
    private void cancelCreation(final ActionEvent event) {
        loader.getMainMenuScene();
        setter.getStage(event).close();
    }

    @FXML
    private void continueCreation(final ActionEvent event) {
        if (this.dbName.getText().trim().isBlank()) {
            setter.warningDialog("Choose a database name");
        } else if (this.dbDescription.getText().trim().isBlank()) {
            setter.warningDialog("Choose a database description");
        } else {
            this.data.takeDBName(this.dbName.getText().toString());
            this.data.takeDBDesc(this.dbDescription.getText().toString());
            this.loader.getSceneData(this.data, ChooseEncrSetController.class);
            this.setter.getStage(event).close();
        }
    }
}
