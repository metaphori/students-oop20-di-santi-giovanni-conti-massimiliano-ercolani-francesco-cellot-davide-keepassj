package view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.db.Database;
import model.db.Entry;
import model.db.Group;
import util.GeneratePasswordRandom;
import util.GeneratePasswordRandomImpl;
import util.PasswordStrengthImpl;
import util.PasswordValidator;
import util.PasswordValidatorImpl;

public class AddEntryController implements Initializable {
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();
    private Database db;
    private Entry entry = null;

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
    private ProgressBar progressBar;

    @FXML
    private Label lblStrength;

    @FXML
    private Label lblPassword;

    @FXML
    final void addNewGroup(final ActionEvent event) {
        loader.getSceneGroup(db);
        setter.getStage(event).close();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    }

    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
    }

    /**
     * Takes database and Entry to edit from previous fxml file.
     * @param db is the database
     * @param entry to edit
     */
    public final void takeDatabase(final Database db, final Entry entry) {
        this.db = db;
        loadGroup();
        this.entry = entry;
        this.title.setText(entry.getNameAccount());
        this.username.setText(entry.getUsername());
        this.password.setText(entry.getPassword());
        this.comboBoxGroup.getSelectionModel().select(entry.getGroupName());
        this.url.setText(entry.getUrl());
        this.notes.setText(entry.getNote());
    }

    @FXML
    final void cancel(final ActionEvent event) {
        if (!(this.entry == null)) {
            this.db.addEntry(this.entry);
        }
        loader.getSceneDb(db);
        setter.getStage(event).close();
    }

    /**
     * Method that checks the presence of fields
     * saves the data entered by the user
     * check if the password is valid
     * and save the new entry in the database.
     * @param event
     */
    @FXML
    final void confirmAdd(final ActionEvent event) {
        if (this.title.getText().isEmpty()) {
            setter.showDialog("Choose a Title for your Entry", AlertType.ERROR);
            return;
        }
        if (this.username.getText().isEmpty()) {
            setter.showDialog("Choose a username", AlertType.ERROR);
            return;
        }
        if (this.password.getText().isEmpty()) {
            setter.showDialog("Choose a password", AlertType.ERROR);
            return;
        }
        if (comboBoxGroup.getSelectionModel().isEmpty()) {
            setter.showDialog("Choose a group", AlertType.ERROR);
            return;
        }

        final String nameAccount = this.title.getText();
        final String username = this.username.getText();
        final String password = this.password.getText();
        final Group group = new Group(comboBoxGroup.getSelectionModel().getSelectedItem());
        final String url = this.url.getText();
        final String note = this.notes.getText();

        final PasswordValidator validate = new PasswordValidatorImpl();

        if (validate.isValid(this.password.getText())) {
            //String tempGroup = comboBoxGroup.getSelectionModel().getSelectedItem();
            db.addEntry(new Entry(nameAccount, username, password, group, url, note));

            try {
                db.writeXml();
            } catch (JAXBException e) {
                e.printStackTrace();
                setter.showDialog("wrong password or database corrupted, something wrong while encrypte xml", AlertType.ERROR);
            }
            loader.getSceneDb(db);
            setter.getStage(event).close();
        } else {
            //If password is not valid, a message will display...
            createAlert();
        }

    }

    private void createAlert() {
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        //Follow the instructions to enter a valid password
        alert.setContentText("Password non valida!\n"
                            + "La password deve contenere:\n"
                            + "1) Almeno 8 caratteri\n"
                            + "2) Almeno un numero");

        alert.showAndWait();
    }

    /**
     * Method to generate a random password
     * and enter it in the passwordField.
     * @param event
     */
    @FXML
    final void generatePassword(final ActionEvent event) {
           final GeneratePasswordRandom generate = new GeneratePasswordRandomImpl();
           this.password.setText(generate.generatePassword());
           updateProgressBar(null);
    }

    @FXML
    final void selectGroup(final ActionEvent event) {
        //System.out.println(this.comboBoxGroup.getSelectionModel().getSelectedItem() + " is the group selected");
    }

    /**
     * Method to update the progressBar status,
     * and display the strength percentage.
     * @param event
     */
    @FXML
    final void updateProgressBar(final KeyEvent event) {
        final double strength = (double) (PasswordStrengthImpl.getStrength(password.getText())) / 100;
        final String str = Double.toString(strength * 100);
        lblStrength.setText(str + "%");
        progressBar.setProgress(strength);
    }

    /**
     * Method to show the password in clear text.
     * @param event
     */
    @FXML
    final void showPassword(final ActionEvent event) {
        lblPassword.setText(password.getText());
    }


    /**
     * Method to load the groupList into the comboBox.
     */
    public final void loadGroup() {
        final ObservableList<String> listGroup = FXCollections.observableArrayList(db.getAllGroup()
                                                                                     .stream()
                                                                                     .map(Group::getName)
                                                                                     .collect(Collectors.toList()));
        this.comboBoxGroup.setItems(listGroup);
    }
}
