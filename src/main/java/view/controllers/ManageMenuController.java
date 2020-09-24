package view.controllers;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ManageMenuController {
    private FxmlFilesLoader entryLoader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    private FxmlFilesLoader groupLoader = new FxmlFilesLoaderImpl("/view/database/AddGroup.fxml");

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> usernameColumn;

    @FXML
    private TableColumn<?, ?> passwordColumn;

    @FXML
    private TableColumn<?, ?> groupColumn;

    @FXML
    private TableColumn<?, ?> notesColumn;

    @FXML
    private TableColumn<?, ?> urlColumn;

    @FXML
    void addEntry(ActionEvent event) {
        //todo
        
        entryLoader.getScene();
    }

    @FXML
    void addGroup(ActionEvent event) {
        //todo
        
        groupLoader.getScene();
    }

}
