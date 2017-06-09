package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class AddComplaintResponse extends MyUtilModel {

    DAddComplaintClass d;

    public DAddComplaintClass getD() {
        return d;
    }

    public void setD(DAddComplaintClass d) {
        this.d = d;
    }
}
