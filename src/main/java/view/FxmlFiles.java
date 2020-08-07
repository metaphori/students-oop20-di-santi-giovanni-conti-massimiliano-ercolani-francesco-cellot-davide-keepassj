package view;

public enum FxmlFiles  {
    
    /**
     * menu scene
     */
    
    MAINMENU("MainMenuView.fxml");
    
    /**
     * path for FXMLLoader
     */
    private static final String PATH = "/view/";
    
    private final String name;
    
    /**
     * 
     * @param name is of the file to be loaded
     */
    FxmlFiles(final String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return the path of the fxml file to be loaded
     */
    public String getFxmlPath() {
        return PATH + this.name;
    }
}
