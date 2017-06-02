package sheet.bottom.com.networklib.models.tecoutil;

import sheet.bottom.com.networklib.models.global.MyUtilModel;

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
