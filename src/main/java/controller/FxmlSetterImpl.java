package controller;

import java.util.EventObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * Class that implements FxmlSetter interface.
 *
 */
public class FxmlSetterImpl implements FxmlSetter {

    @Override
    public final void setSpinner(final Spinner<Integer> spinner, final Integer min, final Integer max) {
        final SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        spinner.setValueFactory(values);
        spinner.setEditable(true);
    }

    @Override
    public final boolean showDialog(final String message, final AlertType type) {
        final AlertType alType = type;
        final Alert alert = new Alert(alType, "");

        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText(message);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            return true;
        } 
        return false;
    }

    @Override
    public final Stage getStage(final ActionEvent event) {
        final Stage stage = (Stage) ((Node) ((EventObject) event).getSource()).getScene().getWindow();
        return stage;
    }
}

