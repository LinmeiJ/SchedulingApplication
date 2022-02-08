package enums;

public enum Views {
    ADD_NEW_CUSTOMER_VIEW("../views/addNewCustomerView.fxml"),
    CUSTOMER_RECORD_VIEW("../views/customerRecordView.fxml"),
    NEW_APT_VIEW("../views/addNewAptView.fxml"),
    UPDATE_CUSTOMER_VIEW("../views/updateCustView.fxml"),
    UPDATE_APPOINTMENT_VIEW("../views/updateAptView.fxml"),
    APPOINTMENT_RECORD_VIEW("../views/aptRecordView.fxml"),
    REPORT_VIEW("../views/reportsView.fxml"),
    LOGIN_VIEW_PATH("../views/loginView.fxml");

    private final String view;

    Views(final String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }
}
