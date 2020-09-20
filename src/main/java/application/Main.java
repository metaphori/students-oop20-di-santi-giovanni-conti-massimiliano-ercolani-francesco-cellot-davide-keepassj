package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import view.ViewImpl;

/*
 * keepassj main
 *
 */
public class Main extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        View view = new ViewImpl();
        view.loadFirstScene(stage);
    }
    
    public static void main(final String[] args) {
        Application.launch();
    }

}