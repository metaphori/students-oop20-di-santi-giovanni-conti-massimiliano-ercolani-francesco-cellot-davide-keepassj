package view.controllers;

import java.awt.TextField;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class AddEntryController {
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    FxmlFilesLoader loaderGroup = new FxmlFilesLoaderImpl("/view/database/AddGroup.fxml");
    FxmlSetter setter = new FxmlSetterImpl();

    @FXML
    private TextField title;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField url;

    @FXML
    private TextField notes;

    @FXML
    void addNewGroup(ActionEvent event) {
        loaderGroup.getScene();
        setter.getStage(event).close();
    }

    @FXML
    void cancel(ActionEvent event) {
        loader.getManageMenuScene();
        setter.getStage(event).close();
    }

    @FXML
    void confirmAdd(ActionEvent event) {
        //todo
        
        
        
        loader.getMainMenuScene();
        setter.getStage(event).close();
    }

    @FXML
    void generatePassword(ActionEvent event) {

    }

    @FXML
    void selectGroup(ActionEvent event) {

    }
}
