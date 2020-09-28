package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import view.ViewImpl;

/**
 * 
 * Main for application start.
 *
 */
public class StartFX extends Application {

    @Override
    public final void start(final Stage stage) throws Exception {
        View view = new ViewImpl();
        view.loadFirstScene(stage);
    }

    /**
     * KeePassJ launch.
     * @param args 
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
