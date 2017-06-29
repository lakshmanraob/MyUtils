package my.util.app;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import my.util.app.models.BillDetails;
import my.util.app.models.LoginResult;
import my.util.app.models.MyAuthResponse;
import my.util.app.utils.Constants;
import my.util.app.utils.DbHelper;
import my.util.app.utils.PrefsHelper;

public class DataManager {

    private static DataManager mInstance;
    private static DbHelper mDbHelper;
    private static Context mContext;

    private static ArrayList<BillDetails> mBillList;
    private static BillDetails mCurrentBill;
    private boolean isAccountDetailsConfirmed;
    private String accNo;
    private String ssn;
    private String userCsrfToken;
    private String userCookie1;
    private String userCookie2;
    private MyAuthResponse allComplaints;

    private DataManager(Context ctx) {
        mContext = ctx;
    }

    public static DataManager getInstance(Context ctx){
        if (mInstance == null) {
            mInstance = new DataManager(ctx);
        }
        return mInstance;
    }

    public void clearData(){
        mInstance = null;
    }

    public void init(){
        Log.d("DEBUG_LOG", "DataManager : init called");
        mDbHelper = new DbHelper(mContext);
    }

    public synchronized DbHelper getDbHelper() {
        return mDbHelper;
    }

    /*public ArrayList<IssueDetails> fetchAllSavedComplaints(){
        ArrayList<IssueDetails> allComplaints = mDbHelper.getAllComplaints();
        if (allComplaints == null || allComplaints.size() <= 0) {
            Log.d("DEBUG_LOG", "no complaints data exists : add dummy data");
            mComplaintsList = Constants.getDummyComplaintsList(mContext);
            for(IssueDetails issue: mComplaintsList){
                mDbHelper.addNewComplaint(issue);
            }
        } else {
            mComplaintsList = allComplaints;
        }
        return mComplaintsList;
    }*/

    public ArrayList<BillDetails> fetchAllSavedBills(){
        ArrayList<BillDetails> allBills = mDbHelper.getAllBills();
        if (allBills == null || allBills.size() <= 0) {
            Log.d("DEBUG_LOG", "no bill data exists : add dummy data");
            mBillList = Constants.getDummyBillsList();
            for(BillDetails bill: mBillList){
                mDbHelper.addNewBill(bill);
            }
        } else {
            mBillList = allBills;
        }
        return mBillList;
    }

    public BillDetails getmCurrentBill() {
        return mCurrentBill;
    }

    public void setmCurrentBill(BillDetails mCurrentBill) {
        DataManager.mCurrentBill = mCurrentBill;
    }

    public boolean isAccountDetailsConfirmed() {
        return isAccountDetailsConfirmed;
    }

    public void setAccountDetailsConfirmed(boolean accountDetailsConfirmed) {
        isAccountDetailsConfirmed = accountDetailsConfirmed;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        PrefsHelper.getInstance().setStringPref(Constants.ACC_NUMBER, accNo);
        this.accNo = accNo;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        PrefsHelper.getInstance().setStringPref(Constants.SSN_DIGITS, ssn);
        this.ssn = ssn;
    }

    public String getUserCsrfToken() {
        return userCsrfToken;
    }

    public void setUserCsrfToken(String userCsrfToken) {
        this.userCsrfToken = userCsrfToken;
    }

    public String getUserCookie1() {
        return userCookie1;
    }

    public void setUserCookie1(String userCookie1) {
        this.userCookie1 = userCookie1;
    }

    public String getUserCookie2() {
        return userCookie2;
    }

    public void setUserCookie2(String userCookie2) {
        this.userCookie2 = userCookie2;
    }

    public MyAuthResponse getAllComplaints() {
        return allComplaints;
    }

    public void setAllComplaints(MyAuthResponse allComplaints) {
        this.allComplaints = allComplaints;
    }

    public LoginResult[] getComplaintsList() {
        return allComplaints.getD().getResults();
    }
}
