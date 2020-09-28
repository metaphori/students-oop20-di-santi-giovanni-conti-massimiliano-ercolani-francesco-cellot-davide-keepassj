package controller;

import java.util.EventObject;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * Class that implements FxmlSetter interface.
 *
 */
public class FxmlSetterImpl implements FxmlSetter {

    @Override
    public void setSpinner(final Spinner<Integer> spinner, final Integer min, final Integer max) {
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        spinner.setValueFactory(values);
        spinner.setEditable(true);
    }

    @Override
    public void warningDialog(final String warningMessage) {
        AlertType type = AlertType.WARNING;
        Alert alert = new Alert(type, "");
        
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText(warningMessage);
        alert.showAndWait();
    }

    @Override
    public Stage getStage(final ActionEvent event) {
        Stage stage = (Stage) ((Node) ((EventObject) event).getSource()).getScene().getWindow();
        return stage;
    }

    @Override
    public void infoDiaog() {
        AlertType type = AlertType.INFORMATION;
        Alert alert = new Alert(type, "");
        
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.getDialogPane().setContentText("KeePassJ was created by:\n\n"
                + "· Giovanni Di Santi\n"
                + "· Francesco Ercolani\n"
                + "· Massimiliano Conti\n"
                + "· Davide Cellot");
        alert.showAndWait();       
    }
}

