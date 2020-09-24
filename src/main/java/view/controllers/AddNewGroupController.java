package view.controllers;

import java.awt.TextField;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddNewGroupController {
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    FxmlSetter setter = new FxmlSetterImpl();
    
    @FXML
    private TextField title;

    @FXML
    private TextField username;

    @FXML
    void cancel(ActionEvent event) {
        loader.getScene();
        setter.getStage(event).close();
    }

    @FXML
    void confirmAdd(ActionEvent event) {
        //todo
        
        
        loader.getScene();
        setter.getStage(event).close();
    }
}
