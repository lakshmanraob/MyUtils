package my.util.app.utils;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import my.util.app.R;

public class Constants {

    public static final String CAPTURE_IMAGE_NAME = "capture_image";
    public static final int CAMERA_REQUEST_CODE = 1001;
    public static final int IMAGE_COUNT = 3;
    public static final int IMAGE_CORNER = 25;
    public static final String EMERGENCY_NUMBER = "911";
    public static final int LOCATION_PERMISSIONS_CODE = 1;
    public static final int CAMERA_PERMISSIONS_CODE = 2;
    public static final int PERMISSIONS_CODE = 1;
    public static final String ELECTRICITY_SERVICE = "Electricity Services";
    public static final String GAS_SERVICE = "Gas Services";
    public static final String TECO = "TECO";

    public final class COMPLAINT_STATUS {
        public static final int SUBMITTED = 1;
        public static final int UNDER_REVIEW = 2;
        public static final int RESOLVED = 3;
    }

    public final class USER_ADDRESSES {
        public static final String HOME = "3275 NW 24th Street Rd, Bangalore";
        public static final String OFFICE = "3284 NW 26th Street Rd, Bangalore";
    }

    public static ArrayList<IssueDetails> getComplaintsList(Context ctx) {
        ArrayList<IssueDetails> complaints = new ArrayList<>();
        Resources res = ctx.getResources();
        complaints.add(new IssueDetails(res.getString(R.string.streetlight_outage), "05/05/2017",
                USER_ADDRESSES.HOME, COMPLAINT_STATUS.SUBMITTED));
        complaints.add(new IssueDetails(res.getString(R.string.power_outage), "05/05/2017",
                USER_ADDRESSES.HOME, COMPLAINT_STATUS.UNDER_REVIEW));
        complaints.add(new IssueDetails(res.getString(R.string.safety_concern), "04/05/2017",
                USER_ADDRESSES.OFFICE, COMPLAINT_STATUS.RESOLVED));
        complaints.add(new IssueDetails(res.getString(R.string.power_outage), "04/05/2017",
                USER_ADDRESSES.HOME, COMPLAINT_STATUS.RESOLVED));

        return complaints;
    }

    private static ArrayList<BillDetails> prepareBill() {
        ArrayList<BillDetails> addressList = new ArrayList<>();
        addressList.add(new BillDetails(USER_ADDRESSES.HOME, BILL_TYPES.ELECTRICITY,
                TECO, "100Kwh", null, "$12", "April 2017", null, null, null, null, null, null));
        addressList.add(new BillDetails(USER_ADDRESSES.OFFICE, BILL_TYPES.ELECTRICITY,
                TECO, "643Kwh", null, "$63", "May 2017", null, null, null, null, null, null));
        addressList.add(new BillDetails(USER_ADDRESSES.OFFICE, BILL_TYPES.GAS,
                TECO, "1.576 Thm", null, "$107", "May 2017", null, null, null, null, null, null));
        return addressList;
    }

    public static HashMap<String, List<BillDetails>> getRecentBills() {
        HashMap<String, List<BillDetails>> hashMap = new HashMap<String, List<BillDetails>>();
        ArrayList<BillDetails> bills = prepareBill();
        for (BillDetails bill : bills) {
            String key = bill.getAddress();
            if (hashMap.containsKey(key)) {
                List<BillDetails> list = hashMap.get(key);
                list.add(bill);
            } else {
                List<BillDetails> list = new ArrayList<BillDetails>();
                list.add(bill);
                hashMap.put(key, list);
            }
        }
        return hashMap;
    }

    public static List<String> getBillTitles() {
        List<String> titles = new ArrayList<>();
        titles.add(USER_ADDRESSES.HOME);
        titles.add(USER_ADDRESSES.OFFICE);
        return titles;
    }

    public final class BILL_TYPES {
        public static final int ELECTRICITY = 1;
        public static final int GAS = 2;
    }
}
