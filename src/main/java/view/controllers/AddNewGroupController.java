package view.controllers;

import javax.xml.bind.JAXBException;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.db.Database;
import model.db.Group;

/**
 * 
 * Controller for AddGroup.fxml file.
 *
 */
public class AddNewGroupController {
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl("/view/database/AddEntry.fxml");
    private final FxmlSetter setter = new FxmlSetterImpl();
    private Database db;
    private Boolean goToManageMenu = true;

    @FXML
    private TextField groupName;

    @FXML
    private TextField groupDesc;

    @FXML
    final void cancel(final ActionEvent event) {
        if (setter.showDialog("Are you sure you want to cancel? Data will be lost.", AlertType.CONFIRMATION)) {
            if (goToManageMenu) {
                loader.getSceneDb(db);
            } else {
                loader.getSceneEntry(db);
            }
            setter.getStage(event).close();
        }
    }

    @FXML
    final void confirmAdd(final ActionEvent event) {
        db.addGroup(new Group(groupName.getText(), groupDesc.getText()));
        try {
            db.writeXml();
        } catch (JAXBException e) {
            e.printStackTrace();
            setter.showDialog("wrong password or database corrupted, something wrong while encrypte xml", AlertType.ERROR);
        }
        if (goToManageMenu) {
            loader.getSceneDb(db);
        } else {
            loader.getSceneEntry(db);
        }
        setter.getStage(event).close();
    }

    /**
     * Takes database from previous fxml file.
     * @param db is the database
     */
    public final void takeDatabase(final Database db) {
        this.db = db;
    }

    /**
     * Method for specifying if controller has to go to manage menu or not.
     * @param goToManageMenu is the flag
     */
    public final void setGoToManageMenu(final Boolean goToManageMenu) {
        this.goToManageMenu = goToManageMenu;
    }
}
