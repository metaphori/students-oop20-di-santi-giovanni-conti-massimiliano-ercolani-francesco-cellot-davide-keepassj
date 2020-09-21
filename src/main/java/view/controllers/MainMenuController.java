package view.controllers;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainMenuController {
    
    final String source = "/view/createnew/chooseNameDb.fxml";
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    FxmlSetter setter = new FxmlSetterImpl();
    
    @FXML
    public void createNewDatabase(ActionEvent event) throws Exception{
        loader.getScene();
        setter.getStage(event).close();
    }
    
    @FXML
    public void openDatabase(ActionEvent event) {
        
    }
}
