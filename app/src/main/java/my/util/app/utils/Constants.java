package my.util.app.utils;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.models.BillDetails;
import my.util.app.models.IssueDetails;

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
    public static final String CSRF_TOKEN = "X-CSRF-Token";
    public static final String USER_COOKIE = "set-cookie";
    public static final int ADD_MIN_LENGTH = 10;

    public static final double LAT_ECITY = 12.84598;
    public static final double LONG_ECITY = 77.66394;
    public static final double LAT_HOSAROAD = 12.87038;
    public static final double LONG_HOSAROAD = 77.65793;
    public static final double LAT_BOSH = 12.86891;
    public static final double LONG_BOSH = 77.66389;
    public static final double LAT_HSR = 12.90758;
    public static final double LONG_HSR = 77.63951;
    public static final double LAT_OFC = 12.94383;
    public static final double LONG_OFC = 77.69033;
    public static final double LAT_MRTHLI = 12.95613;
    public static final double LONG_MRTHLI = 77.70975;
    public static final double LAT_PEENYA = 13.03533;
    public static final double LONG_PEENYA = 77.52691;

    public static final int ACC_NO_LEN = 6;
    public static final int REF_NO_LEN = 6;

    public static final String PAGE_TITLE = "pageTitle";
    public static final String PAGE_NUMBER = "pageNumber";

    //For displaying the Information display
    public static final long INFO_DISPLAY_TIME = 5000L;

    public static final long PROGRESS_TIME = 1000;
    public static final long PROCESS_TIME = 500;

    public static final int LOCATION_UPDATE_REQUEST_IN_METERS = 1600; // 1 mile = 1.6 km

    public static final String DATE_FORMAT = "MM/dd/yyyy";

    public final class FRAGMENTS {
        public static final int BILLS = 0;
        public static final int COMPLAINTS = 1;
        public static final int ACCOUNT = 2;
        public static final int NEW_COMPLAINT = 3;
        public static final int BILL_DETAILS = 4;
        public static final int PAY_ACCOUNT_DETAILS = 5;
        public static final int ISSUES_MAP_VIEW = 6;
    }

    public final class COMPLAINT_STATUS {
        public static final int SUBMITTED = 1;
        public static final int UNDER_REVIEW = 2;
        public static final int RESOLVED = 3;
    }

    public final class USER_ADDRESSES {
        public static final String ECITY = "4059 Dolphin Dr \nTampa, FL";
        public static final String HOSAROAD = "203 S Hubert Avenue, \nTampa, FL";
        public static final String BOSH = "2408 Gordon St, \nTampa, FL";
        public static final String HSR = "1120 E Kennedy BLVD Unit 814, \nTampa, FL";
        public static final String OFC = "3228 W Horbar Ave, \nTampa, FL";
        public static final String MRTHLI = "3106 W Paxton Ave, \nTampa, FL";
        public static final String PEENYA = "914E Ida St, \nTampa, FL";
    }

    public static ArrayList<IssueDetails> getDummyComplaintsList(Context ctx) {
        Resources res = ctx.getResources();
        ArrayList<IssueDetails> complaints = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 7);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.streetlight_outage), cal,
                USER_ADDRESSES.OFC, COMPLAINT_STATUS.SUBMITTED, Constants.LAT_OFC, Constants.LONG_OFC, Utils.getRandom()));
        cal.set(Calendar.DAY_OF_MONTH, 8);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.safety_concern), cal,
                USER_ADDRESSES.OFC, COMPLAINT_STATUS.SUBMITTED, Constants.LAT_OFC, Constants.LONG_OFC, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 5);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.power_outage), cal,
                USER_ADDRESSES.MRTHLI, COMPLAINT_STATUS.UNDER_REVIEW, Constants.LAT_MRTHLI, Constants.LONG_MRTHLI, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 4);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.safety_concern), cal,
                USER_ADDRESSES.ECITY, COMPLAINT_STATUS.SUBMITTED, Constants.LAT_ECITY, Constants.LONG_ECITY, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.power_outage), cal,
                USER_ADDRESSES.PEENYA, COMPLAINT_STATUS.UNDER_REVIEW, Constants.LAT_PEENYA, Constants.LONG_PEENYA, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 27);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.power_outage), cal,
                USER_ADDRESSES.HOSAROAD, COMPLAINT_STATUS.RESOLVED, Constants.LAT_HOSAROAD, Constants.LONG_HOSAROAD, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.streetlight_outage), cal,
                USER_ADDRESSES.BOSH, COMPLAINT_STATUS.RESOLVED, Constants.LAT_BOSH, Constants.LONG_BOSH, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.power_outage), cal,
                USER_ADDRESSES.HSR, COMPLAINT_STATUS.RESOLVED, Constants.LAT_HSR, Constants.LONG_HSR, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.others), cal,
                USER_ADDRESSES.OFC, COMPLAINT_STATUS.RESOLVED, Constants.LAT_OFC, Constants.LONG_OFC, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 28);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.safety_concern), cal,
                USER_ADDRESSES.MRTHLI, COMPLAINT_STATUS.RESOLVED, Constants.LAT_MRTHLI, Constants.LONG_MRTHLI, Utils.getRandom()));
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 7);
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.YEAR, 2017);
        complaints.add(new IssueDetails(res.getString(R.string.safety_concern), cal,
                USER_ADDRESSES.MRTHLI, COMPLAINT_STATUS.UNDER_REVIEW, Constants.LAT_MRTHLI, Constants.LONG_MRTHLI, Utils.getRandom()));
        return Utils.sortComplaintsList(complaints);
    }

    public static ArrayList<BillDetails> getDummyBillsList() {
        ArrayList<BillDetails> addressList = new ArrayList<>();
        addressList.add(new BillDetails(USER_ADDRESSES.MRTHLI,
                BILL_TYPES.ELECTRICITY, TECO,
                "100",
                "1.4",
                12,
                "April 2017",
                "03/05/2017 - 04/04/2017",
                "20/04/2017",
                "345627",
                "Economy 100 Plan",
                "$0.12",
                190));
        addressList.add(new BillDetails(USER_ADDRESSES.MRTHLI,
                BILL_TYPES.GAS, TECO,
                "70",
                "0.8",
                14,
                "April 2017",
                "03/05/2017 - 04/04/2017",
                "20/04/2017",
                "345628",
                "Economy House Rural Plan",
                "$0.15",
                210));
        addressList.add(new BillDetails(USER_ADDRESSES.OFC,
                BILL_TYPES.ELECTRICITY, TECO,
                "110",
                "1.4",
                12,
                "Mar 2017",
                "02/05/2017 - 03/04/2017",
                "20/03/2017",
                "333896",
                "Economy 100 Plan",
                "$0.12",
                240));
        addressList.add(new BillDetails(USER_ADDRESSES.HSR,
                BILL_TYPES.ELECTRICITY, TECO,
                "63",
                "1.4",
                11,
                "Mar 2017",
                "02/05/2017 - 03/04/2017",
                "20/03/2017",
                "3289002",
                "Economy 100 Plan",
                "$0.12",
                155));
        return addressList;
    }


    public static BillDetails getFriendsBill() {
        return new BillDetails(USER_ADDRESSES.MRTHLI,
                BILL_TYPES.ELECTRICITY, TECO,
                "120",
                "1.4",
                12,
                "April 2017",
                "03/05/2017 - 04/04/2017",
                "20/04/2017",
                "345627",
                "Economy 100 Plan",
                "$0.12",
                144);
    }

    public static HashMap<String, List<BillDetails>> getRecentBills(Context ctx) {
        HashMap<String, List<BillDetails>> hashMap = new HashMap<String, List<BillDetails>>();
        ArrayList<BillDetails> bills = DataManager.getInstance(ctx).fetchAllSavedBills();
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
        titles.add(USER_ADDRESSES.MRTHLI);
        titles.add(USER_ADDRESSES.OFC);
        return titles;
    }

    public static List<String> getBillTitles(HashMap<String, List<BillDetails>> hashmap) {
        List<String> titles = new ArrayList<>();
        Iterator<Map.Entry<String, List<BillDetails>>> itr = hashmap.entrySet().iterator();
        while (itr.hasNext()) {
            titles.add(itr.next().getKey());
        }
        return titles;
    }

    public final class BILL_TYPES {
        public static final int ELECTRICITY = 1;
        public static final int GAS = 2;
    }

    public final class COMPLAINTS_TIMINGS {
        public static final int THIS_WEEK = 1;
        public static final int THIS_MONTH = 2;
        public static final int PREVIOUS = 3;
    }

    public final class OUTAGE_TYPE {
        public static final int STREET_LIGHT_OUTAGE = 0;
        public static final int SAFETY_CONCERN = 1;
        public static final int POWER_OUTAGE = 2;
        public static final int OTHER_OUTAGE = 3;
    }

}
