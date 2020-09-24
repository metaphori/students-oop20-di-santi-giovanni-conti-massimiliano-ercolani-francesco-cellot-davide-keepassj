package view.controllers;

import java.io.File;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * Controller for MainMenu.fxml file.
 *
 */
public class MainMenuController {

    private final String source = "/view/createnew/chooseNameDb.fxml";
    private FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    private FxmlSetter setter = new FxmlSetterImpl();
    private FileChooser fileChooser;
    private String fileExtension;

    /**
     * Method for gui button that creates a new database.
     * @param event 
     * @throws Exception 
     */
    @FXML
    public void createNewDatabase(final ActionEvent event) throws Exception {
        loader.getScene();
        setter.getStage(event).close();
    }

    /**
     * Method for gui button that opens an existing database.
     * @param event 
     */
    @FXML
    public void openDatabase(final ActionEvent event) {
        fileChooser = new FileChooser();
        Stage stage = setter.getStage(event);

        fileChooser.setTitle("Open database ");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            System.out.println(fileExtension);
            if (fileExtension.equals("kdbj")) {
                loader.getSceneFile(file);
                setter.getStage(event).close();
            } else {
                setter.warningDialog("Impossibile aprire il database");
            }
        }
    }
}
