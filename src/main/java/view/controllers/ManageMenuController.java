package view.controllers;

import javax.xml.bind.JAXBException;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private Label labelNomeDatabase;


    /**
     * Takes database from previous fxml file.
     * @param db is the database
     * @throws JAXBException 
     */
    public final void takeDatabase(final Database db) throws JAXBException {
        this.db = db;
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toList());
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toCollection(ArrayList::new));
        //ObservableList<Entry> app = FXCollections.<Entry>observableArrayList();
        accountTable.getItems().setAll(db.getAllEntry());
        this.labelNomeDatabase.setText(this.db.getNomeDatabase());
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
