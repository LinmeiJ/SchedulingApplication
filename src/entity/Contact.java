package entity;

public class Contact {
    private int contact_id;
    private String contract_name;
    private String email;

    public int getContact_id() {
        return contact_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
