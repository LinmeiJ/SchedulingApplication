package controller;

import Dao.Validator;
import enums.CountryId;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public interface CommonUseHelperIfc {
     String ADD_NEW_CUSTOMER_VIEW = "../views/addNewCustomerView.fxml";
     String CUSTOMER_RECORD_VIEW = "../views/customerRecordView.fxml";
     String NEW_APT_VIEW = "../views/addNewAptView.fxml";
     String UPDATE_CUSTOMER_VIEW = "../views/updateCustView.fxml";
     String UPDATE_APPOINTMENT_VIEW = "../views/updateAptView.fxml";

    /**
     * This method closes the the program.
     * It prompts a dialog window to ensure whether a user wants to exit.
     *
     * @param event an event indicates a component-defined action occurred
     */
    default void exit(ActionEvent event, Button exitId) {
        Stage stage = (Stage) exitId.getScene().getWindow();
        Validator.displayExitConfirmation();
        if (Validator.confirmResult.isPresent() && Validator.confirmResult.get() == ButtonType.OK) {
            stage.close();
        }
    }

    /**
     * set a scene based on this particular action and fxml path passed over to the params.
     *
     * @param event an event indicates a component-defined action occurred
     * @param s     the file path where the fxml is located at
     * @throws IOException it happens when the fxml file is not found
     */
    default void setScene(ActionEvent event, String s) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(s));
        var scene = new Scene(parent);
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
