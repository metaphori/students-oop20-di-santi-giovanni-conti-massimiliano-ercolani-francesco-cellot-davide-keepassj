package view.controllers;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.db.Database;
import model.db.Group;

/**
 * 
 * Controller for AddGroup.fxml file.
 *
 */
public class AddNewGroupController {
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    private final FxmlSetter setter = new FxmlSetterImpl();
    private Database db;

    @FXML
    private TextField groupName;

    @FXML
    private TextField groupDesc;

    @FXML
    final void cancel(final ActionEvent event) {
        loader.getSceneEntry(db);
        setter.getStage(event).close();
    }

    @FXML
    final void confirmAdd(final ActionEvent event) {
        db.addGroup(new Group(groupName.getText(), groupDesc.getText()));
        loader.getSceneEntry(db);
        setter.getStage(event).close();
    }

    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
    }
}
