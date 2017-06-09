package my.util.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import my.util.app.fragments.ComplaintsFragment;

public class FetchLocationAddress extends IntentService {

    public static final String FETCH_LOCATION = "location";
    public static final String DETECTED_ADDRESS = "address";

    public FetchLocationAddress() {
        super("FetchLocationAddress");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchLocationAddress(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        Location location = (Location) intent.getExtras().getParcelable(FETCH_LOCATION);

        String detected = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            detected = null;
            ioException.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException) {
            detected = null;
            illegalArgumentException.printStackTrace();
        }

        if (addresses == null || addresses.size() == 0) {
            detected = null;
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            detected = TextUtils.join(System.getProperty("line.separator"),
                    addressFragments);
        }
        //return detected;
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ComplaintsFragment.ComplaintReceiver.COMPLAINT_LOCATION_ACTION);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(DETECTED_ADDRESS, detected);
        sendBroadcast(broadcastIntent);
    }
}
