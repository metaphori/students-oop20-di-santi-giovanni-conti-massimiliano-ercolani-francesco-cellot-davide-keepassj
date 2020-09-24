package controller;

import java.util.EventObject;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FxmlSetterImpl implements FxmlSetter {

    @Override
    public void setSpinner(final Spinner<Integer> spinner, final Integer min, final Integer max) {
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
        spinner.setValueFactory(values);
        spinner.setEditable(true);
    }

    @Override
    public void warningDialog(final String warningMessage) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(new Group(new Text(100, 150, warningMessage)));
        dialog.setScene(scene);
        dialog.show();
    }

    @Override
    public Stage getStage(final ActionEvent event) {
        Stage stage = (Stage) ((Node) ((EventObject) event).getSource()).getScene().getWindow();
        return stage;
    }
}
