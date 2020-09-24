package view.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import controller.DBDataSaver;
import controller.DBDataSaverImpl;
import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.crypto.KDFBadParameter;
import model.kdbx.KDB;
import model.kdbx.KDBHeader;

public class ChoosePassController {
    private FxmlFilesLoader loader = new FxmlFilesLoaderImpl();
    private DBDataSaver data = new DBDataSaverImpl();
    private FxmlSetter setter = new FxmlSetterImpl();
    private KDBHeader header = new KDBHeader();
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
        loader.getMainMenuScene();
        setter.getStage(event).close();
    }

    @FXML
    private void confirmCreation(final ActionEvent event) {
        System.out.println(data.getDBDesc());
        if (passwordRepeat.getText().equals(password.getText())) {
            header.setComment(data.getDBName().getBytes());
            header.setPublicCustomData(data.getDBDesc().getBytes());

            header.setCipher(data.getCipher());
            header.setKDF(data.getKdf());
            header.setTransformRounds(data.getRounds());
            if (data.getTweakable()) {
                try {
                    header.setKDFMemory(data.getMemory());
                    header.setKDFParallelism(data.getParallelism());
                } catch (KDFBadParameter e) {
                    // TODO Auto-generated catch block
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
                    setter.warningDialog(".kdbj extension missing");
                } else {
                    creadentials = Arrays.asList(password.getText().getBytes());
                    loader.getMainMenuScene();
                    setter.getStage(event).close();
                    try {
                        database = new KDB(file, creadentials, header);
                        database.write(new byte[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                        }
                }
            }
        } else {
            setter.warningDialog("Passwords are not the same");
        }
    }
}
