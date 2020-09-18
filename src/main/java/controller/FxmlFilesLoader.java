package controller;

public interface FxmlFilesLoader {
    
    /**
     * Get fxml file resource and load the stage
     */
    void getScene();
    
    /**
     * Get fxml file resource, pass the data and load the stage
     * @param data is the data to be passed
     * @param controllerclass is the class of the controller
     */
    void getSceneData(DBDataSaver data, Class<?>controllerClass);
}
