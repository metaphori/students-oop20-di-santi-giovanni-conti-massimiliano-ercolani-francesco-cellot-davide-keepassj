package controller;

import java.awt.TextArea;

import javafx.scene.Group;
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
    public void warningDialog(String warningMessage) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(new Group(new Text(100, 150, warningMessage)));
        dialog.setScene(scene);
        dialog.show();
    }
}
