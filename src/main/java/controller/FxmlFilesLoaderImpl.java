package controller;

import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.db.Database;
import view.controllers.AddEntryController;
import view.controllers.AddNewGroupController;
import view.controllers.ChooseEncrSetController;
import view.controllers.ChoosePassController;
import view.controllers.ManageMenuController;
import view.controllers.OpenDatabaseController;

/**
 * 
 * Class that implements FxmlFilesLoader interface.
 *
 */
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
    public final void getScene() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.source));
            final Parent root1 = (Parent) fxmlLoader.load();
            final Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void getSceneData(final DBDataSaver data, final Class<?> controllerClass) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.source));
            final Parent root1 = (Parent) fxmlLoader.load();

            if (controllerClass == ChooseEncrSetController.class) {
                final ChooseEncrSetController encrController = fxmlLoader.<ChooseEncrSetController>getController();
                encrController.takeData(data);
            } else if (controllerClass == ChoosePassController.class) {
                final ChoosePassController passController = fxmlLoader.<ChoosePassController>getController();
                passController.takeData(data);
            }
            final Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void getMainMenuScene() {
        this.source = "/view/MainMenuView.fxml";
        this.getScene();
    }

    @Override
    public final void getManageMenuScene() {
        this.source = "/view/database/ManageMenu.fxml";
        this.getScene();
    }

    @Override
    public final void getSceneFile(final File file) {
       try {
           final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/OpenDatabase.fxml"));
           final Parent root1 = (Parent) fxmlLoader.load();
           final OpenDatabaseController controller = fxmlLoader.<OpenDatabaseController>getController();
           controller.takeFile(file);
           final Stage stage = new Stage();
           stage.setScene(new Scene(root1));
           stage.show();

       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    public final void getSceneDb(final Database db) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/ManageMenu.fxml"));
            final Parent root1 = (Parent) fxmlLoader.load();
            final ManageMenuController controller = fxmlLoader.<ManageMenuController>getController();
            controller.takeDatabase(db);
            final Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * getScene that open the view to add new entry to db.
     * @param db
     */
    @Override
    public void getSceneEntry(final Database db) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/AddEntry.fxml"));
            final Parent root1 = (Parent) fxmlLoader.load();
            final AddEntryController controller = fxmlLoader.<AddEntryController>getController();
            controller.takeDatabase(db);
            controller.loadGroup();
            final Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getScene that open the view to add new Group to db.
     * @param db
     */
    @Override
    public void getSceneGroup(final Database db) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/AddGroup.fxml"));
            final Parent root1 = (Parent) fxmlLoader.load();
            final AddNewGroupController controller = fxmlLoader.<AddNewGroupController>getController();
            controller.takeDatabase(db);
            final Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
