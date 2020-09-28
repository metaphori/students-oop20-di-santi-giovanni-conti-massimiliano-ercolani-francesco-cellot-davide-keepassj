package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

public interface FxmlSetter {

    /**
     * 
     * Set javafx component spinner for integer
     * @param spinner is the spinner to be set
     * @param min is the minimum limit
     * @param max is the maximum limit
     */
    void setSpinner(Spinner<Integer> spinner, Integer min, Integer max);

    /**
     * Set a javafx dialog box.
     * @param warningMessage is the message shown in the dialog box
     */
    void warningDialog(String warningMessage);

    /**
     * Get the stage from every controller.
     * @param event is the event or the current clicked element 
     * @return the stage
     */
    Stage getStage(ActionEvent event);

    /**
     * Get "about" info.
     */
    void infoDiaog();

}
