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

    }

    @FXML
    void continueCreation(ActionEvent event) {
        data.takeDBName(this.dbName.getText().getBytes());
        data.takeDBDesc(this.dbDescription.getText().getBytes());
        
        loader.getSceneData(data, ChooseNameDBController.class);
        setter.getStage(event).close();
    }
}
