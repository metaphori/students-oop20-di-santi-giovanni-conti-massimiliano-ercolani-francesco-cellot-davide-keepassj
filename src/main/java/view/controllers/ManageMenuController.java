package view.controllers;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import model.db.Database;

/**
 * 
 * Controller for ManageMenu.fxml file.
 *
 */
public class ManageMenuController {
    private FxmlFilesLoader entryLoader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    private FxmlFilesLoader groupLoader = new FxmlFilesLoaderImpl("/view/database/AddGroup.fxml");
    Database db = new Database();

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

    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
    }

    @FXML
    final
    void addEntry(final ActionEvent event) {
        //todo

        entryLoader.getScene();
    }

    @FXML
    final
    void addGroup(final ActionEvent event) {
        //todo

        groupLoader.getScene();
    }

}
