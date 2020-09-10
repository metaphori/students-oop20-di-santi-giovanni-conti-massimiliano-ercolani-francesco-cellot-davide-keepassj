package application;

import javafx.application.Application;
import javafx.stage.Stage;

/*
 * keepassj main
 *
 */
public class Main extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("KeePassJ");
        stage.show();
        
    }
    
    public static void main(final String[] args) {
        launch();
    }

}