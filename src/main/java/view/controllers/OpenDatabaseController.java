package view.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.AEADBadTagException;
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import model.db.Database;
import model.kdbx.KDB;

/**
 * 
 * Controller for OpenDatabase.fxml file.
 *
 */
public class OpenDatabaseController {

    private File file;
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();

    @FXML
    private PasswordField password;

    /**
     * Method that takes the file passed by the previous controller.
     * @param file is the file passed from the previous controller
     */
    public final void takeFile(final File file) {
        this.file = file;
    }
    @FXML
    final void cancel(final ActionEvent event) {
        if (setter.showDialog("Do you want to go back to the main menu?", AlertType.CONFIRMATION)) {
            loader.getMainMenuScene();
            setter.getStage(event).close();
        }
    }

    @FXML
    final void openDatabase(final ActionEvent event) {
        List<byte[]> credentials;
        KDB database = null;

        credentials = Arrays.asList(password.getText().getBytes());

        try {
            database = new KDB(this.file, credentials);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            setter.showDialog("File not found or not correctly selected", AlertType.ERROR);
            return;
        }

        final Database db = new Database(database);

        try {
            db.readXml();
            loader.getSceneDb(db);
            setter.getStage(event).close();
        } catch (AEADBadTagException e) {
            setter.showDialog("Wrong password or database corrupted", AlertType.ERROR);
        }
    }
}
