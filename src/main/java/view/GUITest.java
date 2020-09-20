package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class GUITest extends Application {
    public static void main(final String[] args) {
        Application.launch();
    }

    public final void start(final Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/buba.fxml"));
            Parent root1 = fxmlLoader.load();
            
            stage.setTitle("KeePassJ");
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
