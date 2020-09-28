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

    @FXML
    final void cancel(final ActionEvent event) {
        loader.getSceneDb(db);
        setter.getStage(event).close();
    }

    @FXML
    final void confirmAdd(final ActionEvent event) {
        if (this.title.getText().isEmpty()) {
            setter.warningDialog("Choose a Title for your Entry");
            return;
        }
        if (this.username.getText().isEmpty()) {
            setter.warningDialog("Choose a username");
            return;
        }
        if (this.password.getText().isEmpty()) {
            setter.warningDialog("Choose a password");
            return;
        }
        if (comboBoxGroup.getSelectionModel().isEmpty()) {
            setter.warningDialog("Choose a group");
            return;
        }

        final String nameAccount = this.title.getText();
        final String username = this.username.getText();
        final String password = this.password.getText();
        final Group group = new Group(comboBoxGroup.getSelectionModel().getSelectedItem());
        final String url = this.url.getText();
        final String note = this.notes.getText();

        PasswordValidator validate = new PasswordValidatorImpl();

        if (validate.isValid(this.password.getText())) {
            //String tempGroup = comboBoxGroup.getSelectionModel().getSelectedItem();
            db.addEntry(new Entry(nameAccount, username, password, group, url, note));

            try {
                db.writeXml();
            } catch (JAXBException e) {
                e.printStackTrace();
                setter.warningDialog("wrong password or database corrupted, something wrong while encrypte xml");
            }
            loader.getSceneDb(db);
            setter.getStage(event).close();
        } else {
            //If password is not valid, a message will display...
            createAlert();
        }

    }

    private void createAlert() {
        Alert alert = new Alert(AlertType.ERROR);
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
     * and enter it in the passwordField
     * @param event
     */
    @FXML
    final void generatePassword(final ActionEvent event) {
           GeneratePasswordRandom generate = new GeneratePasswordRandomImpl();
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
        double strength = (double) (PasswordStrengthImpl.getStrength(password.getText())) / 100;
        String str = Double.toString(strength * 100);
        lblStrength.setText(str + "%");
        progressBar.setProgress(strength);
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
