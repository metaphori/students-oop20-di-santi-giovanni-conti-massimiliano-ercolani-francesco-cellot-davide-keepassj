package view.controllers;

import java.awt.event.ActionEvent;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import javafx.fxml.FXML;

public class MainMenuController {
    
    final String source = "src/main/resources/view/createnew/chooseNameDb.fxml";
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    
    @FXML
    public void createNewDatabase(ActionEvent event) throws Exception{
        loader.getScene();
    }
    
    @FXML
    public void openDatabase(ActionEvent event) {
        
    }
}
