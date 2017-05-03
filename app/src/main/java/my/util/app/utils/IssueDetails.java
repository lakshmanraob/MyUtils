package my.util.app.utils;

public class IssueDetails {


    private String outageType;
    private String complaintDate;
    private String address;
    private int status;

    public IssueDetails(String outageType, String complaintDate, String address, int status) {
        this.outageType = outageType;
        this.complaintDate = complaintDate;
        this.address = address;
        this.status = status;
    }

    public String getOutageType() {
        return outageType;
    }

    public void setOutageType(String outageType) {
        this.outageType = outageType;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
