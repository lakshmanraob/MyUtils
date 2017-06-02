package sheet.bottom.com.networklib.models.tecoutil;

import sheet.bottom.com.networklib.models.global.MyUtilModel;

public class DAddComplaintClass extends MyUtilModel {

    MetaData __metadata;

    String Bpart;
    String FromDate;
    String ToDate;
    String Qmnum;
    String Qmtxt;
    String RetMessage;

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

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getQmnum() {
        return Qmnum;
    }

    public void setQmnum(String qmnum) {
        Qmnum = qmnum;
    }

    public String getQmtxt() {
        return Qmtxt;
    }

    public void setQmtxt(String qmtxt) {
        Qmtxt = qmtxt;
    }

    public String getRetMessage() {
        return RetMessage;
    }

    public void setRetMessage(String retMessage) {
        RetMessage = retMessage;
    }
}
