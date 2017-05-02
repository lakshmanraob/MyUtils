package my.util.app.utils;

public class RecentBillsItem {

    private String address;

    private BillDetails billData;

    public RecentBillsItem(String address, BillDetails billData) {
        this.address = address;
        this.billData = billData;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BillDetails getBillData() {
        return billData;
    }

    public void setBillData(BillDetails billData) {
        this.billData = billData;
    }
}
