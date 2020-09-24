package view.controllers;

import java.awt.TextField;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * 
 * Controller for AddGroup.fxml file.
 *
 */
public class AddNewGroupController {
    private FxmlFilesLoader loader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    private FxmlSetter setter = new FxmlSetterImpl();

    @FXML
    private TextField title;

    @FXML
    private TextField username;

    @FXML
    final
    void cancel(final ActionEvent event) {
        loader.getScene();
        setter.getStage(event).close();
    }

    @FXML
    final
    void confirmAdd(final ActionEvent event) {
        //todo


        loader.getScene();
        setter.getStage(event).close();
    }
}
