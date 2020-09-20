package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewImpl implements View {

    @Override
    public void loadFirstScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/view/MainMenuView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            
            stage.setTitle("KeePassJ");
            stage.setScene(new Scene(root1));  
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
