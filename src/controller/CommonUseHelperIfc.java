package controller;

import dao.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

public interface CommonUseHelperIfc {
     ObservableList<String> estHr = FXCollections.observableArrayList(Arrays.asList("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"));
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
    default void setScene(ActionEvent event, String s){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(s));
            var scene = new Scene(parent);
            var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default ObservableList<String> initializeMinutes(){
        ObservableList<String> initMinutes = FXCollections.observableArrayList();

        for(int i = 0; i <= 45;){
            if(i < 10){  initMinutes.add("0" + i);}
            else initMinutes.add(String.valueOf(i));
            i = i + 15;
        }
        return initMinutes;
    }

    default Callback<DatePicker, DateCell> getDayCellFactory() {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.getDayOfWeek() == DayOfWeek.SATURDAY
                                || item.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

}
