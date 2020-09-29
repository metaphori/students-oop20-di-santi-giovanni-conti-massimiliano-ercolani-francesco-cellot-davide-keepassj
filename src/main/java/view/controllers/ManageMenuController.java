package view.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
    private Boolean isPasswordVisible = false;


    @FXML
    private TableView<Entry> accountTable;

    @FXML
    private TableView<Group> groupTable;

    @FXML
    private Label labelNomeDatabase;

    @FXML
    private Button btnVisibility;

    @SuppressWarnings("unchecked")
    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        final TableColumn<Entry, String> title = new TableColumn<>("Title");
        final TableColumn<Entry, String> username = new TableColumn<>("Username");
        final TableColumn<Entry, String> password = new TableColumn<>("Password");
        final TableColumn<Entry, String> group = new TableColumn<>("Group"); 
        final TableColumn<Entry, String> url = new TableColumn<>("Url");
        final TableColumn<Entry, String> note = new TableColumn<>("Note");

        this.accountTable.getColumns().addAll(title, username, password, group, url, note);

        title.setCellValueFactory(new PropertyValueFactory<>("nameAccount"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        group.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        url.setCellValueFactory(new PropertyValueFactory<>("url"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));

        final TableColumn<Group, String> nameGroup = new TableColumn<>("Name");
        final TableColumn<Group, String> descGroup = new TableColumn<>("Description");
        this.groupTable.getColumns().addAll(nameGroup, descGroup);
        nameGroup.setCellValueFactory(new PropertyValueFactory<>("name"));
        descGroup.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void setPasswordColumn() {
        this.accountTable.getColumns().remove(2);
        final TableColumn<Entry, String> password = new TableColumn<>("Password");
        this.accountTable.getColumns().add(2, password);
        if (this.isPasswordVisible) {
            password.setCellValueFactory(new PropertyValueFactory<>("password"));
        } else {
            password.setCellValueFactory(new PropertyValueFactory<>("stringNull"));
        }
    }

    /**
     * method to auto adjust width of columns in the TableView.
     */
    public final void autoResizeColumns() {
        //Set the right policy
        final Double space = 30d;
        this.accountTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        this.accountTable.getColumns().stream().forEach((column) -> {
            //Minimal width = columnheader
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < this.accountTable.getItems().size(); i++) {
                //cell must not be empty
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    final double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth(max + space);
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

    /**
     * method to change the boolean addicted to show/hide password in TableView.
     * @param event
     */
    @FXML
    final void changePasswordVisibility(final ActionEvent event) {
        this.isPasswordVisible = !isPasswordVisible;
        if (this.isPasswordVisible) {
            this.btnVisibility.setText("hide password");
        } else {
            this.btnVisibility.setText("show password");
        }
        updateTableView();
    }

    /**
     * method to adjust the width of column in TableView.
     */
    private void updateTableView() {
        setPasswordColumn();
        final ObservableList<Entry> entryTemp = FXCollections.observableArrayList(this.db.getAllEntry());
        accountTable.setItems(entryTemp);
        autoResizeColumns();
        final ObservableList<Group> groupTemp = FXCollections.observableArrayList(db.getAllGroup());
        groupTable.setItems(groupTemp);
    }

    @FXML
    final void addEntry(final ActionEvent event) {
        entryLoader.getSceneEntry(db);
        setter.getStage(event).close();
    }

    @FXML
    final void addGroup(final ActionEvent event) {
        groupLoader.getSceneGroup(db, true);
        setter.getStage(event).close();
    }

    @FXML
    final void deleteSelectedEntry(final ActionEvent event) {
        final ArrayList<Entry> p = new ArrayList<>(this.accountTable.getSelectionModel().getSelectedItems());
        /*for (Entry res : p) {
            this.db.deleteEntry(res);
          }*/
        p.stream().forEach(e -> this.db.deleteEntry(e));
        try {
            this.db.writeXml();
        } catch (JAXBException e1) {
            e1.printStackTrace();
            setter.showDialog("Error while Database write while deleting Entry", AlertType.ERROR);
        }
        //Entry entryTemp = this.accountTable.getSelectionModel().getSelectedItem();
        //this.db.deleteEntry(entryTemp);
        updateTableView();
    }

    @FXML
    final void editEntry(final ActionEvent event) {
        final Entry p = this.accountTable.getSelectionModel().getSelectedItem();
        if (p == null) {
            return;
        }
        this.db.deleteEntry(p);
        entryLoader.getSceneEntry(this.db, p);
        setter.getStage(event).close();
    }

    @FXML
    final void deleteGroup(final ActionEvent event) {
        final Group p = this.groupTable.getSelectionModel().getSelectedItem();
        if (this.db.deleteGroup(p)) {
            try {
                this.db.writeXml();
            } catch (JAXBException e1) {
                e1.printStackTrace();
                setter.showDialog("Error while Database write while deleting group", AlertType.ERROR);
            }
        } else {
            setter.showDialog("It's impossible to delete a group if 1 or more Entries are in that group", AlertType.ERROR);
        }
        updateTableView();
    }

    @FXML
    final void infoApp(final ActionEvent event) {
        setter.showDialog("KeePassJ was created by:\n\n"
                + "· Giovanni Di Santi\n"
                + "· Francesco Ercolani\n"
                + "· Massimiliano Conti\n"
                + "· Davide Cellot", AlertType.INFORMATION);
    }

    @FXML
    final void closeApp(final ActionEvent event) {
        if (setter.showDialog("Are you sure you want to close the database?", AlertType.CONFIRMATION)) {
            System.exit(0);
        }
    }

}
