package controller;

import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controllers.ChooseEncrSetController;
import view.controllers.ChoosePassController;
import view.controllers.OpenDatabaseController;


public class FxmlFilesLoaderImpl implements FxmlFilesLoader {

    private String source;

    /**
     * Empty constructor.
     */
    public FxmlFilesLoaderImpl() {
    }

    /**
     * Constructor which takes fxml source.
     * @param source is the fxml source
     */
    public FxmlFilesLoaderImpl(final String source) {
        this.source = source;
    }

    @Override
    public void getScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.source));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSceneData(final DBDataSaver data, final Class<?>controllerClass) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.source));
            Parent root1 = (Parent) fxmlLoader.load();

            if (controllerClass == ChooseEncrSetController.class) {
                ChooseEncrSetController encrController = fxmlLoader.<ChooseEncrSetController>getController();
                encrController.takeData(data);
            } else if (controllerClass == ChoosePassController.class) {
                ChoosePassController passController = fxmlLoader.<ChoosePassController>getController();
                passController.takeData(data);
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMainMenuScene() {
        this.source = "/view/MainMenuView.fxml";
        this.getScene();
    }

    @Override
    public void getSceneFile(File file) {
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/OpenDatabase.fxml"));
           Parent root1 = (Parent) fxmlLoader.load();
           OpenDatabaseController controller = fxmlLoader.<OpenDatabaseController>getController();
           controller.takeFile(file);
           Stage stage = new Stage();
           stage.setScene(new Scene(root1));
           stage.show();

       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}