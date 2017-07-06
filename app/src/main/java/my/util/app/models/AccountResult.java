package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class AccountResult extends MyUtilModel {

    MetaData __metadata;
    String Bpart;
    String FirstName;
    String LastName;
    String Dob;
    String Phone;
    String Email;
    String Address;

    public MetaData get__metadata() {
        return __metadata;
    }

    public void set__metadata(MetaData __metadata) {
        this.__metadata = __metadata;
    }

    public String getBpart() {
        return Bpart;
    }

    public void setBpart(String bpart) {
        Bpart = bpart;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
