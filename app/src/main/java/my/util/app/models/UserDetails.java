package my.util.app.models;

import java.util.HashMap;
import java.util.List;

public class UserDetails {

    private String accountNumber;
    private String firstName;
    private String lastName;
    private String dob;
    private String phoneNumber;
    private String email;
    private String address;

    HashMap<String, List<BillDetails>> serviceBillDetailsList;

    public UserDetails(String accountNumber, String first,
                       String last,
                       String dob,
                       String phoneNumber,
                       String email,
                       String address,
                       HashMap<String, List<BillDetails>> billDetails) {
        this.accountNumber = accountNumber;
        this.firstName = first;
        this.lastName = last;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.serviceBillDetailsList = billDetails;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashMap<String, List<BillDetails>> getServiceBillDetailsList() {
        return serviceBillDetailsList;
    }

    public void setServiceBillDetailsList(HashMap<String, List<BillDetails>> serviceBillDetailsList) {
        this.serviceBillDetailsList = serviceBillDetailsList;
    }
}


