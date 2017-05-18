package my.util.app.models;

import my.util.app.utils.Utils;

public class BillDetails {

    private String address;
    private int billType;
    private String provider;
    private String consumption;
    private String consumptionAverage;
    private int amount;
    private String billingDate;
    private String billingCycle;
    private String payByDate;
    private String meterNumber;
    private String planName;
    private String planCost;
    private int total;

    public BillDetails() {
    }

    public BillDetails(String address, int billType, String provider, String consumption,
                       String consumptionAverage, int amount, String billingDate, String billingCycle,
                       String payByDate, String meterNumber, String planName, String planCost, int total) {
        this.address = address;
        this.billType = billType;
        this.provider = provider;
        this.consumption = consumption;
        this.consumptionAverage = consumptionAverage;
        this.amount = amount;
        this.billingDate = billingDate;
        this.billingCycle = billingCycle;
        this.payByDate = payByDate;
        this.meterNumber = meterNumber;
        this.planName = planName;
        this.planCost = planCost;
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getConsumptionAverage() {
        return consumptionAverage;
    }

    public void setConsumptionAverage(String consumptionAverage) {
        this.consumptionAverage = consumptionAverage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBillingDate() {
        return Utils.convertDate(billingDate);
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getPayByDate() {
        return Utils.convertDate(payByDate);
    }

    public void setPayByDate(String payByDate) {
        this.payByDate = payByDate;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanCost() {
        return planCost;
    }

    public void setPlanCost(String planCost) {
        this.planCost = planCost;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
