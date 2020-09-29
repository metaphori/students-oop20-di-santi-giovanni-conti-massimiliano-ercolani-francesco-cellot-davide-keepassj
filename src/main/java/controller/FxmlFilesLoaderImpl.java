package controller;

import java.io.File;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.db.Database;
import model.db.Entry;
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

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
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
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void getMainMenuScene() {
        this.source = "/view/MainMenuView.fxml";
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.source));
            final Parent root1 = (Parent) fxmlLoader.load();
            final Stage stage = new Stage();

            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void getSceneFile(final File file) {
       try {
           final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/OpenDatabase.fxml"));
           final Parent root1 = (Parent) fxmlLoader.load();
           final OpenDatabaseController controller = fxmlLoader.<OpenDatabaseController>getController();
           controller.takeFile(file);

           final Stage stage = new Stage();
           stage.initStyle(StageStyle.UNDECORATED);
           stage.setResizable(false);
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
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
               private FxmlSetter setter = new FxmlSetterImpl();
                @Override
                public void handle(final WindowEvent event) {
                    if (setter.showDialog("Do you really want to close the database?", AlertType.CONFIRMATION)) {
                        System.exit(0);
                    } else {
                        event.consume();
                    }
                }
            });
            stage.setResizable(false);
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
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getScene that open the view to edit an entry off db.
     * @param db
     */
    @Override
    public void getSceneEntry(final Database db, final Entry entry) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/AddEntry.fxml"));
            final Parent root1 = (Parent) fxmlLoader.load();
            final AddEntryController controller = fxmlLoader.<AddEntryController>getController();
            controller.takeDatabase(db, entry);
            controller.loadGroup();

            final Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getScene that open the view to add new Group to db.
     * @param db
     * @param goToManageMenu
     */
    @Override
    public void getSceneGroup(final Database db, final Boolean goToManageMenu) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/database/AddGroup.fxml"));
            final Parent root1 = (Parent) fxmlLoader.load();
            final AddNewGroupController controller = fxmlLoader.<AddNewGroupController>getController();
            controller.takeDatabase(db);
            controller.setGoToManageMenu(goToManageMenu);

            final Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
