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

public class ChooseNameDBController {
    
    final String source = "/view/createnew/chooseEncryptionSet.fxml";
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl(source);
    DBDataSaver data = new DBDataSaverImpl();
    FxmlSetter setter = new FxmlSetterImpl();
    
    @FXML
    private TextField dbName;
    
    @FXML
    private TextField dbDescription;

    @FXML
    void cancelCreation(ActionEvent event) {
        setter.getStage(event).close();
    }

    @FXML
    void continueCreation(ActionEvent event) {    
        if(this.dbName.getText().trim().isBlank()) {
            setter.warningDialog("Choose a database name");
        } else if(this.dbDescription.getText().trim().isBlank()) {
            setter.warningDialog("Choose a database description");
        } else {
            this.data.takeDBName(this.dbName.getText().getBytes());
            this.data.takeDBDesc(this.dbDescription.getText().getBytes());
            loader.getSceneData(data, ChooseNameDBController.class);
            setter.getStage(event).close();
        }
    }
}
