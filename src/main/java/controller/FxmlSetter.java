package controller;

import javafx.scene.control.Spinner;

public interface FxmlSetter {

    /**
     * 
     * Set javafx component spinner for integer
     * @param spinner is the spinner to be set
     * @param min is the minimum limit
     * @param max is the maximum limit
     */
    void setSpinner(Spinner<Integer> spinner, Integer min, Integer max);
    
}
