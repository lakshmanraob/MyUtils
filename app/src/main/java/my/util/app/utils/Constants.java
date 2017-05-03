package my.util.app.utils;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

import my.util.app.R;

public class Constants {

    public static final String CAPTURE_IMAGE_NAME = "capture_image";
    public static final int CAMERA_REQUEST_CODE = 1001;
    public static final int IMAGE_COUNT = 3;
    public static final int IMAGE_CORNER = 25;
    public static final String EMERGENCY_NUMBER = "911";
    public static final int LOCATION_PERMISSIONS_CODE = 1;
    public static final int CAMERA_PERMISSIONS_CODE = 2;

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
}
