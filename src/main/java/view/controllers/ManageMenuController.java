package view.controllers;
/*
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.crypto.AEADBadTagException;
*/
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.db.Database;
import model.db.Entry;

/**
 * 
 * Controller for ManageMenu.fxml file.
 *
 */
public class ManageMenuController {
    private final FxmlFilesLoader entryLoader = new FxmlFilesLoaderImpl();
    private final FxmlFilesLoader groupLoader = new FxmlFilesLoaderImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();
    private Database db = new Database();


    @FXML
    private TableView<Entry> accountTable;


    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toList());
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toCollection(ArrayList::new));
        //ObservableList<Entry> app = FXCollections.<Entry>observableArrayList();
        accountTable.getItems().setAll(db.getAllEntry());
    }

    @FXML
    final void addEntry(final ActionEvent event) {
        entryLoader.getSceneEntry(db);
        setter.getStage(event).close();
    }

    @FXML
    final void addGroup(final ActionEvent event) {
        groupLoader.getSceneGroup(db);
        setter.getStage(event).close();
    }

}
