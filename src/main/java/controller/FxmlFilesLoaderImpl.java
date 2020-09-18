package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxmlFilesLoaderImpl implements FxmlFilesLoader{
    
    final String source;
    
    public FxmlFilesLoaderImpl(String source) {
        this.source = source;
    }
    
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
    
}