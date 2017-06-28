package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class LoginResult extends MyUtilModel {

    MetaData __metadata;
    String Bpart;
    String OutageType;
    String LatiServAdd;
    String FromDate;
    String IssueDate;
    String LongServAdd;
    String ToDate;
    String IssueStatus;
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

    public String getOutageType() {
        return OutageType;
    }

    public void setOutageType(String outageType) {
        OutageType = outageType;
    }

    public String getLatiServAdd() {
        return LatiServAdd;
    }

    public void setLatiServAdd(String latiServAdd) {
        LatiServAdd = latiServAdd;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getLongServAdd() {
        return LongServAdd;
    }

    public void setLongServAdd(String longServAdd) {
        LongServAdd = longServAdd;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getIssueStatus() {
        return IssueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        IssueStatus = issueStatus;
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
