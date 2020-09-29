package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

/**
 * 
 * A class for doing recurrent actions.
 *
 */
public interface FxmlSetter {

    /**
     * 
     * Set javafx component spinner for integer.
     * @param spinner is the spinner to be set
     * @param min is the minimum limit
     * @param max is the maximum limit
     */
    void setSpinner(Spinner<Integer> spinner, Integer min, Integer max);

    /**
     * Set a javafx dialog box.
     * @param message is the message shown in the dialog box
     * @param type is the type of alert
     * @return true is user press ok button, false otherwise
     */
    boolean showDialog(String message, AlertType type);

    /**
     * Get the stage from every controller.
     * @param event is the event or the current clicked element 
     * @return the stage
     */
    Stage getStage(ActionEvent event);

}
