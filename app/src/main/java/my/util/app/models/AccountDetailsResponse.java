package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class AccountDetailsResponse extends MyUtilModel {

    DAccountClass d;

    public DAccountClass getD() {
        return d;
    }

    public void setD(DAccountClass d) {
        this.d = d;
    }
}
