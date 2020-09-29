package controller;

import java.io.File;

import model.db.Database;
import model.db.Entry;

/**
 * 
 * Interface for fxml files loading in different ways.
 *
 */
public interface FxmlFilesLoader {
    /**
     * Get fxml file resource and load the stage.
     */
    void getScene();

    /**
     * Get fxml file resource, pass the data and load the stage.
     * @param data is the data to be passed
     * @param controllerClass is the class of the controller
     */
    void getSceneData(DBDataSaver data, Class<?> controllerClass);

    /**
     * Get fxml file of mainMenuView.
     */
    void getMainMenuScene();

    /**
     * Get fxml file passing the file.
     * @param file is the file to be passed
     */
    void getSceneFile(File file);

    /**
     * Get fxml file passing the database.
     * @param db is database to be passed
     */
    void getSceneDb(Database db);

    /**
     * Get fxml file passing the db (for Group).
     * @param db is the database
     * @param goToManageMenu is the flag to know what action is to be chosen
     */
    void getSceneGroup(Database db, Boolean goToManageMenu);

    /**
     * Get fxml file passing the db (for Entry).
     * @param db is the database
     */
    void getSceneEntry(Database db);

    /**
     * getScene that open the view to edit an entry off db.
     * @param db is the database
     * @param entry is the entry
     */
    void getSceneEntry(Database db, Entry entry);
}
