package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class GUITest extends Application {
    public static void main(final String[] args) {
        launch(args);
    }

    public final void start(final Stage primaryStage) {
        Label label = new Label("Hello World");
        Scene scene = new Scene(label);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
