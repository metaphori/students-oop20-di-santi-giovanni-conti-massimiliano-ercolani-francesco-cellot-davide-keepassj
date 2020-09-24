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
import model.db.Database;
import model.kdbx.KDB;

/**
 * 
 * Controller for OpenDatabase.fxml file.
 *
 */
public class OpenDatabaseController {

    private File file;
    private List<byte[]> credentials;
    private Database db;
    private KDB database;
    private FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    private FxmlSetter setter = new FxmlSetterImpl();

    @FXML
    private PasswordField password;

    public void takeFile(final File file) {
        this.file = file;
    }
    @FXML
    void cancel(final ActionEvent event) {
        loader.getMainMenuScene();
        setter.getStage(event).close();
    }

    @FXML
    void openDatabase(final ActionEvent event) {
        this.credentials = Arrays.asList(password.getText().getBytes());

        try {
            database = new KDB(this.file, this.credentials);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        db = new Database(database);

        try {
            db.readXml();
            loader.getSceneDb(db);
            setter.getStage(event).close();
        } catch (AEADBadTagException e) {
            setter.warningDialog("Wrong password");
        }
    }
}
