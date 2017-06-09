package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class AddComplaintRequest extends MyUtilModel {

    String Bpart;

    public AddComplaintRequest(String bpart) {
        Bpart = bpart;
    }

    public String getBpart() {
        return Bpart;
    }

    public void setBpart(String bpart) {
        Bpart = bpart;
    }
}
