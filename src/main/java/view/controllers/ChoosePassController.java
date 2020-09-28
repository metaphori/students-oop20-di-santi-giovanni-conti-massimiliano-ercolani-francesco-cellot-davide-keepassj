package view.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import controller.DBDataSaver;
import controller.DBDataSaverImpl;
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.crypto.KDFBadParameter;
import model.db.Database;
import model.kdbx.KDB;
import model.kdbx.KDBHeader;

/**
 * 
 * Controller for choosePassMenu.fxml file.
 *
 */
public class ChoosePassController {
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    private DBDataSaver data = new DBDataSaverImpl();
    private final FxmlSetter setter = new FxmlSetterImpl();
    private final KDBHeader header = new KDBHeader();
    private KDB database;
    private FileChooser fileChooser;
    private List<byte[]> creadentials;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordRepeat;

    /**
     * Takes the data from the previous controller.
     * @param data is the data passed by the previous fxml file controller
     */
    public void takeData(final DBDataSaver data) {
        this.data = data;
    }

    @FXML
    private void cancelCreation(final ActionEvent event) {
        if (setter.showDialog("Are you sure you want to abort the creation?\n"
                + "Data will be lost.", AlertType.CONFIRMATION)) {
            loader.getMainMenuScene();
            setter.getStage(event).close();
        }
    }

    @FXML
    private void confirmCreation(final ActionEvent event) throws JAXBException {
        System.out.println(data.getDBDesc());
        if (passwordRepeat.getText().equals(password.getText()) && password.getText().isBlank() == false) {
            header.setComment(data.getDBName().getBytes());
            header.setPublicCustomData(data.getDBDesc().getBytes());

            header.setCipher(data.getCipher());
            header.setKDF(data.getKdf());
            header.setTransformRounds(data.getRounds());
            if (data.isTweakable()) {
                try {
                    header.setKDFMemory(data.getMemory());
                    header.setKDFParallelism(data.getParallelism());
                } catch (KDFBadParameter e) {
                    e.printStackTrace();
                }
            }
            fileChooser = new FileChooser();
            Stage stage = setter.getStage(event);
            fileChooser.setTitle("Save database as");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("KeePassJ database", "*.kdbj"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                String filename = file.getName();
                if (filename.endsWith(".kdbj") == false) {
                    setter.showDialog(".kdbj extension missing", AlertType.ERROR);
                } else {
                    creadentials = Arrays.asList(password.getText().getBytes());
                    loader.getMainMenuScene();
                    setter.getStage(event).close();
                    try {
                        database = new KDB(file, creadentials, header);
                        //database.write(new byte[0]);
                        Database temp = new Database(database);
                        temp.setNomeDatabase(data.getDBName());
                        temp.writeXml();
                    } catch (IOException e) {
                        e.printStackTrace();
                        }
                }
            }
        } else {
            if (passwordRepeat.getText().equals(password.getText()) == false) {
                setter.showDialog("Passwords are not the same", AlertType.ERROR);
            } else if (password.getText().isBlank()) {
                setter.showDialog("Insert a password", AlertType.ERROR);
            }
        }
    }
}
