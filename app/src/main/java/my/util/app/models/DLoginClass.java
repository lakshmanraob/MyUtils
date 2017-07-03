package my.util.app.models;

import my.util.app.network.global.MyUtilModel;

public class DLoginClass extends MyUtilModel {

    MetaData __metadata;
    String Bpart;
    String SsnPwd;
    String Message;

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

    public String getSsnPwd() {
        return SsnPwd;
    }

    public void setSsnPwd(String ssnPwd) {
        SsnPwd = ssnPwd;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
