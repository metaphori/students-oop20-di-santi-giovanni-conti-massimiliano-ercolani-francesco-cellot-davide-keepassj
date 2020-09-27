package view.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import model.db.Database;
import model.db.Entry;

/**
 * 
 * Controller for ManageMenu.fxml file.
 *
 */
public class ManageMenuController implements Initializable {
    private final FxmlFilesLoader entryLoader = new FxmlFilesLoaderImpl();
    private final FxmlFilesLoader groupLoader = new FxmlFilesLoaderImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();
    private Database db = new Database();


    @FXML
    private TableView<Entry> accountTable;

    @FXML
    private Label labelNomeDatabase;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        TableColumn<Entry, String> title = new TableColumn<Entry, String>("Title");
        TableColumn<Entry, String> username = new TableColumn<Entry, String>("Username");
        TableColumn<Entry, String> password = new TableColumn<Entry, String>("Password");
        TableColumn<Entry, String> group = new TableColumn<Entry, String>("Group"); 
        TableColumn<Entry, String> url = new TableColumn<Entry, String>("Url");
        TableColumn<Entry, String> note = new TableColumn<Entry, String>("Note");

        accountTable.getColumns().addAll(title, username, password, group, url, note);

        title.setCellValueFactory(new PropertyValueFactory<Entry, String>("nameAccount"));
        username.setCellValueFactory(new PropertyValueFactory<Entry, String>("username"));
        group.setCellValueFactory(new PropertyValueFactory<Entry, String>("groupName"));
        url.setCellValueFactory(new PropertyValueFactory<Entry, String>("url"));
        note.setCellValueFactory(new PropertyValueFactory<Entry, String>("note"));
    }

    /**
     * Takes database from previous fxml file.
     * @param db is the database
     * @throws JAXBException 
     */
    public final void takeDatabase(final Database db) throws JAXBException {
        this.db = db;
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toList());
        //List<String> app = db.getAllEntry().stream().map(Entry::getNameAccount).collect(Collectors.toCollection(ArrayList::new));
        ObservableList<Entry> listTemp = FXCollections.observableArrayList(db.getAllEntry());
        accountTable.setItems(listTemp);
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
