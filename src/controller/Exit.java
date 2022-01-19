package controller;

import Dao.Validator;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public interface Exit {

    default void exit(ActionEvent event, Button exitId) {
        Stage stage = (Stage) exitId.getScene().getWindow();
        Validator.displayExitConfirmation();
        if (Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK) {
            stage.close();
        }
    }
}
