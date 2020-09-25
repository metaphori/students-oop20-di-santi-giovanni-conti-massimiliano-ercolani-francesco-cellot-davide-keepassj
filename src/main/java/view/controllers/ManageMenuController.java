package view.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.crypto.AEADBadTagException;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.db.Database;
import model.db.Entry;

/**
 * 
 * Controller for ManageMenu.fxml file.
 *
 */
public class ManageMenuController {
    private FxmlFilesLoader entryLoader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    private FxmlFilesLoader groupLoader = new FxmlFilesLoaderImpl("/view/database/AddGroup.fxml");
    private FxmlSetter setter = new FxmlSetterImpl();
    private Database db = new Database();

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

    private void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("nameAccount"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        urlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
    }
    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toList());
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toCollection(ArrayList::new));
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toList());
        initialize();
    }

    @FXML
    final void addEntry(final ActionEvent event) {
        entryLoader.getSceneEntry(db);
        try {
            db.readXml();
        } catch (AEADBadTagException e) {
            e.printStackTrace();
            setter.warningDialog("Impossibile aprire il database");
        }
    }

    @FXML
    final void addGroup(final ActionEvent event) {
        //todo

        groupLoader.getScene();
    }

}
