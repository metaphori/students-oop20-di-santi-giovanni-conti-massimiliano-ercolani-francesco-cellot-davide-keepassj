package controller;

import java.awt.TextArea;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class FxmlSetterImpl implements FxmlSetter {

    @Override
    public void setSpinner(final Spinner<Integer> spinner, final Integer min, final Integer max) {
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        spinner.setValueFactory(values);
        spinner.setEditable(true);
    }
}
