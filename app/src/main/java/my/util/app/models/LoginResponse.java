package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class LoginResponse extends MyUtilModel {

    DLoginClass d;

    public DLoginClass getD() {
        return d;
    }

    public void setD(DLoginClass d) {
        this.d = d;
    }
}
