package view.controllers;

import java.awt.TextField;
import java.util.stream.Collectors;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import model.db.Database;
import model.db.Entry;
import model.db.Group;

public class AddEntryController {
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();
    private Database db;

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
    private ComboBox<String> comboBoxGroup;

    @FXML
    final void addNewGroup(final ActionEvent event) {
        loader.getSceneGroup(db);
        setter.getStage(event).close();
    }

    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
    }

    @FXML
    final void cancel(final ActionEvent event) {
        loader.getSceneDb(db);
        setter.getStage(event).close();
    }

    @FXML
    final void confirmAdd(final ActionEvent event) {
        String tempGroup = comboBoxGroup.getSelectionModel().getSelectedItem();
        db.addEntry(
                new Entry(title.getText(), 
                        username.getText(), 
                        password.getText(), 
                        db.getGroup(tempGroup), 
                        url.getText(), 
                        notes.getText()));
        loader.getSceneDb(db);
        setter.getStage(event).close();
    }

    @FXML
    final void generatePassword(final ActionEvent event) {

    }

    @FXML
    final void selectGroup(final ActionEvent event) {
        this.comboBoxGroup.setItems(FXCollections.observableArrayList(db.getAllGroup()
                                                                        .stream()
                                                                        .map(Group::getName)
                                                                        .collect(Collectors.toList())));
    }
}
