package view.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import controller.DBDataSaver;
import controller.DBDataSaverImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.kdbx.KDB;
import model.kdbx.KDBHeader;

public class ChoosePassController {
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
    
    public void takeData(DBDataSaver data) {
        this.data = data;
    }
    @FXML
    void cancelCreation(ActionEvent event) {

    }

    @FXML
    void confirmCreation(ActionEvent event) {
        if(passwordRepeat.toString() == password.toString()) {
            header.setComment(data.getDBName());
            header.setPublicCustomData(data.getDBDesc());
            header.setCipher(data.getCipher());
            header.setKDF(data.getKdf());
            header.setTransformRounds(data.getRounds());
            if(data.getTweakable() == true) {
                header.setKDFMemory(data.getMemory());
                header.setKDFParallelism(data.getParallelism());
            }
            fileChooser = new FileChooser();
            Stage stage = setter.getStage(event);
            fileChooser.setTitle("Save database as");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
            File file = fileChooser.showSaveDialog(stage);
            creadentials = Arrays.asList(password.toString().getBytes());
            
            try {
                database = new KDB(file, creadentials, header);
                database.write(new byte[0]);
            } catch(IOException e) {
                e.printStackTrace();
            }
            
        } else {
            setter.warningDialog("Passwords are not the same");
        }
    }

    @FXML
    void goBack(ActionEvent event) {

    }
}
