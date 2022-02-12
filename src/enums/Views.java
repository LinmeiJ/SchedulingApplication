package enums;

/**
 * This enum class contains all the view path for this whole application.
 *
 * @author Linmei M.
 */
public enum Views {
    ADD_NEW_CUSTOMER_VIEW("../views/addNewCustomerView.fxml"),
    CUSTOMER_RECORD_VIEW("../views/customerRecordView.fxml"),
    ADD_NEW_APT_VIEW("../views/addNewAptView.fxml"),
    UPDATE_CUSTOMER_VIEW("../views/updateCustView.fxml"),
    UPDATE_APPOINTMENT_VIEW("../views/updateAptView.fxml"),
    APPOINTMENT_RECORD_VIEW("../views/aptRecordView.fxml"),
    REPORT_VIEW("../views/reportsView.fxml"),
    LOGIN_VIEW_PATH("../views/loginView.fxml");

    private final String view;

    /**
     * A constructor for this class
     * @param view the list of views as above
     */
    Views(final String view) {
        this.view = view;
    }

    /**
     * A getter to retrieve the views.
     * @return a view path
     */
    public String getView() {
        return view;
    }
}
