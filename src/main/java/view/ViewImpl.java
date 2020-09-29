package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * Class which implements View interface.
 *
 */
public class ViewImpl implements View {

    @Override
    public final void loadFirstScene(final Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainMenuView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage.setResizable(false);
            stage.setTitle("KeePassJ");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
