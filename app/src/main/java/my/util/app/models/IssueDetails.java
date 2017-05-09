package my.util.app.models;

import java.util.Calendar;

import my.util.app.utils.Utils;

public class IssueDetails {

    private String outageType;
    private Calendar complaintDate;
    private String address;
    private int status;
    private double latitude;
    private double longitude;
    private int complaintTiming;
    private int referenceNo;

    public IssueDetails() {
    }

    public IssueDetails(String outageType, Calendar complaintDate, String address, int status, double latitude, double longitude, int referenceNo) {
        this.outageType = outageType;
        this.complaintDate = complaintDate;
        this.address = address;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.referenceNo = referenceNo;
    }

    public String getOutageType() {
        return outageType;
    }

    public void setOutageType(String outageType) {
        this.outageType = outageType;
    }

    public Calendar getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Calendar complaintDate) {
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getComplaintTiming() {
        return Utils.getComplaintTiming(complaintDate);
    }

    public String getReferenceNo() {
        return String.valueOf(referenceNo);
    }

    public void setReferenceNo(int referenceNo) {
        this.referenceNo = referenceNo;
    }
}
