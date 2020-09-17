package view.controllers;

import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import view.FxmlFilesLoader;

public class MainMenuController {
    
    String source = "src/main/resources/view/createnew/chooseNameDb.fxml";
    FxmlFilesLoader loader = new FxmlFilesLoader(this.source);
    
    @FXML
    public void createNewDatabase(ActionEvent event) throws Exception{
        loader.setScene();
    }
    
    @FXML
    public void openDatabase(ActionEvent event) {
        
    }
}
