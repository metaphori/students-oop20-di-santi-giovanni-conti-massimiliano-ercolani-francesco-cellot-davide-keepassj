package view.controllers;

import java.awt.event.ActionEvent;

import controller.DBDataSaver;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import model.kdbx.KDBHeader;

public class ChoosePassController {
    DBDataSaver data;
    private FxmlSetter setter = new FxmlSetterImpl();
    KDBHeader header = new KDBHeader();
  
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
        if(passwordRepeat == password) {
            header.setComment(data.getDBName());
            header.setPublicCustomData(data.getDBDesc());
            header.setCipher(data.getCipher());
            header.setKDF(data.getKdf());
            //setrounds
            if(data.getTweakable() == true) {
                header.setKDFMemory(data.getMemory());
                header.setKDFParallelism(data.getParallelism());
            }
            
        } else {
            setter.warningDialog("Passwords are not the same");
        }
    }

    @FXML
    void goBack(ActionEvent event) {

    }
}
