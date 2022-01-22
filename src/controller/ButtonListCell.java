package controller;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

class ButtonListCell extends ListCell<Customer> {
    @Override
    public void updateItem(Customer obj, boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(obj.toString());

            Button butt = new Button();
            butt.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Delete");
                }
            });
            setGraphic(butt);
        }
    }
}