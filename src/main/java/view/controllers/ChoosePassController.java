package view.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.codec.binary.Hex;

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
    FxmlFilesLoader loader = new FxmlFilesLoaderImpl("/view/MainMenuView.fxml");
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
        setter.getStage(event).close();
    }

    @FXML
    void confirmCreation(ActionEvent event) {       
        System.out.println(data.getDBDesc());
        if(passwordRepeat.getText().equals(password.getText())) {
            header.setComment(data.getDBName().getBytes());
            header.setPublicCustomData(data.getDBDesc().getBytes());

            header.setCipher(data.getCipher());
            header.setKDF(data.getKdf());
            header.setTransformRounds(data.getRounds());
            if(data.getTweakable() == true) {
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
            
            if(file != null) {
                creadentials = Arrays.asList(password.getText().getBytes());   
                loader.getScene();
                setter.getStage(event).close();
                try {
                    database = new KDB(file, creadentials, header);
                    database.write(new byte[0]);
                } catch(IOException e) {
                    e.printStackTrace();
                    }
            }
            
        } else {
            setter.warningDialog("Passwords are not the same");
        }
    }

    @FXML
    void goBack(ActionEvent event) {

    }
}
