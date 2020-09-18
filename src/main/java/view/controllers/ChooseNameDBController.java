package view.controllers;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.kdbx.KDBHeader;

public class ChooseNameDBController {
    
    final String source = "src/main/resources/view/createnew/chooseEncryptionSet.fxml";
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl(source);
    byte[] databaseName;
    byte[] databaseDescription;
    
    @FXML
    private TextField dbName;
    
    @FXML
    private TextField dbDescription;

    @FXML
    void cancelCreation(ActionEvent event) {

    }

    @FXML
    void continueCreation(ActionEvent event) {
        this.databaseName = this.dbName.getText().getBytes();
        this.databaseDescription = this.dbDescription.getText().getBytes();
        
        loader.getScene();
    }
    
    public byte[] getInsertedDBName() {
        return this.databaseName;
    }
    
    public byte[] getInsertedDBDescription() {
        return this.databaseDescription;
    }

}
