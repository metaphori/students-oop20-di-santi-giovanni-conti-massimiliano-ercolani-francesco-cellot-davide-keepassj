package view.controllers;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.text.Text;
import model.db.Database;
import model.db.Entry;
import model.db.Group;

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
    private TableView<Group> groupTable;

    @FXML
    private Label labelNomeDatabase;

    @SuppressWarnings("unchecked")
    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        TableColumn<Entry, String> title = new TableColumn<Entry, String>("Title");
        TableColumn<Entry, String> username = new TableColumn<Entry, String>("Username");
        TableColumn<Entry, String> password = new TableColumn<Entry, String>("Password");
        TableColumn<Entry, String> group = new TableColumn<Entry, String>("Group"); 
        TableColumn<Entry, String> url = new TableColumn<Entry, String>("Url");
        TableColumn<Entry, String> note = new TableColumn<Entry, String>("Note");

        this.accountTable.getColumns().addAll(title, username, password, group, url, note);

        title.setCellValueFactory(new PropertyValueFactory<Entry, String>("nameAccount"));
        username.setCellValueFactory(new PropertyValueFactory<Entry, String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<Entry, String>("password"));
        group.setCellValueFactory(new PropertyValueFactory<Entry, String>("groupName"));
        url.setCellValueFactory(new PropertyValueFactory<Entry, String>("url"));
        note.setCellValueFactory(new PropertyValueFactory<Entry, String>("note"));

        TableColumn<Group, String> nameGroup = new TableColumn<Group, String>("Name");
        TableColumn<Group, String> descGroup = new TableColumn<Group, String>("Description");
        this.groupTable.getColumns().addAll(nameGroup, descGroup);
        nameGroup.setCellValueFactory(new PropertyValueFactory<Group, String>("name"));
        descGroup.setCellValueFactory(new PropertyValueFactory<Group, String>("description"));
    }

    public final void autoResizeColumns() {
        //Set the right policy
        this.accountTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        this.accountTable.getColumns().stream().forEach((column) -> {
            //Minimal width = columnheader
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < this.accountTable.getItems().size(); i++) {
                //cell must not be empty
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth(max + 10.0d);
        }
        );
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
        updateTableView();
        this.labelNomeDatabase.setText(this.db.getNomeDatabase());
    }

    private void updateTableView() {
        ObservableList<Entry> entryTemp = FXCollections.observableArrayList(db.getAllEntry());
        accountTable.setItems(entryTemp);
        autoResizeColumns();
        ObservableList<Group> groupTemp = FXCollections.observableArrayList(db.getAllGroup());
        groupTable.setItems(groupTemp);
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

    @FXML
    final void deleteSelectedEntry(final ActionEvent event) {
        ArrayList<Entry> p = new ArrayList<>(this.accountTable.getSelectionModel().getSelectedItems());
        /*for (Entry res : p) {
            this.db.deleteEntry(res);
          }*/
        p.stream().forEach(e -> this.db.deleteEntry(e));
        //Entry entryTemp = this.accountTable.getSelectionModel().getSelectedItem();
        //this.db.deleteEntry(entryTemp);
        updateTableView();
    }

}
