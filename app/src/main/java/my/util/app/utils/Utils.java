package my.util.app.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.AuthActivity;
import my.util.app.activity.BaseActivity;
import my.util.app.activity.SignUpActivity;
import my.util.app.adapter.AddressExpandableListAdapter;
import my.util.app.models.BillDetails;

public class Utils {
    private static ProgressDialog mProgressDialog;
    static int prev = -1;

    public static void showShortToast(Context context, String toastMsg) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String toastMsg) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();
    }

    public static void hideKeyboard(Context context, View field) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
    }

    public static boolean checkForInternet(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!isAvailable) {
            showShortToast(ctx, "Please connect to the Internet !");
        }
        return isAvailable;
    }

    public static void showDialog(Context ctx, Dialog.OnKeyListener listener) {
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setOnKeyListener(listener);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static void showProgressDialog(Context ctx) {
        if (ctx != null) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(ctx);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();
            } else {
                mProgressDialog.show();
            }
        }
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static void startGPS(Context ctx, LocationListener listener) {
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        String best = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            Utils.showShortToast(ctx, "Location permissions not granted.");
            return;
        }
        locationManager.requestLocationUpdates(best, 50, 0, listener);
    }

    public static String getCurrentAddress(Context ctx, Location location) {
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        List<Address> addresses = null;
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
        return detected;
    }

    public static void fetchSignUpDetails(final Activity act) {
        showProgressBarWithListener(act, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                showAccountConfirmationDialog(act);
            }
        });
    }

    public static void showAccountConfirmationDialog(final Activity ctx) {
        prev = -1;
        final Dialog dialog = new Dialog(ctx, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.account_confirmation_dialog, null);
        ((IconTextView) view.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final ExpandableListView addressList = (ExpandableListView) view.findViewById(R.id.address_expandable_list);
        HashMap<String, List<BillDetails>> allBills = Constants.getRecentBills(ctx);
        addressList.setAdapter(new AddressExpandableListAdapter(ctx, Constants.getBillTitles(allBills), allBills, true));
        addressList.setGroupIndicator(null);
        addressList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prev != -1 && prev != groupPosition) {
                    addressList.collapseGroup(prev);
                }
                prev = groupPosition;
            }
        });
        View footerView = inflater.inflate(R.layout.ud_footer, null);
        Button btn = (Button) footerView.findViewById(R.id.logout_btn);
        btn.setText("Confirm");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance(ctx).setAccountDetailsConfirmed(true);
                dialog.dismiss();
                ((SignUpActivity) ctx).nextText.performClick();
            }
        });
        addressList.addFooterView(footerView);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showEmergencyCallDialog(final Context ctx) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.emergency_call_dialog, null);
        ((TextView) view.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((TextView) view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constants.EMERGENCY_NUMBER));
                ctx.startActivity(intent);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showSubmitDialog(final Activity ctx, int referenceNumber) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.submit_dialog, null);
        TextView messageTv = (TextView) view.findViewById(R.id.submit_text);
        String message = ctx.getResources().getString(R.string.submit_message_start) + "<b>" + referenceNumber + "</b";
        messageTv.setText(Html.fromHtml(message));
        ((TextView) view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((BaseActivity) ctx).updateFragment(Constants.FRAGMENTS.COMPLAINTS);
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static int getComplaintTiming(Calendar dateToCompare) {
        int complaintTiming = 0;
        Calendar currentCalForWeek = Calendar.getInstance();
        if ((currentCalForWeek.get(Calendar.WEEK_OF_YEAR) == dateToCompare.get(Calendar.WEEK_OF_YEAR)) &&
                (currentCalForWeek.get(Calendar.YEAR) == dateToCompare.get(Calendar.YEAR))) {
            complaintTiming = Constants.COMPLAINTS_TIMINGS.THIS_WEEK;
            return complaintTiming;
        } else {
            Calendar currentCalForMonth = Calendar.getInstance();
            if ((currentCalForMonth.get(Calendar.MONTH) == dateToCompare.get(Calendar.MONTH)) &&
                    (currentCalForWeek.get(Calendar.YEAR) == dateToCompare.get(Calendar.YEAR))) {
                complaintTiming = Constants.COMPLAINTS_TIMINGS.THIS_MONTH;
                return complaintTiming;
            } else {
                complaintTiming = Constants.COMPLAINTS_TIMINGS.PREVIOUS;
                return complaintTiming;
            }
        }
    }

    public static int getRandom() {
        return Constants.REF_NO_LEN < 1 ? 0 :
                new Random().nextInt((9 * (int) Math.pow(10, Constants.REF_NO_LEN - 1)) - 1) +
                        (int) Math.pow(10, Constants.REF_NO_LEN - 1);
    }

    /*public static ArrayList<IssueDetails> sortComplaintsList(ArrayList<IssueDetails> rawList) {
        ArrayList<IssueDetails> sortedList = new ArrayList<>();
        for (IssueDetails issue : rawList) {
            if (Constants.COMPLAINTS_TIMINGS.THIS_WEEK == issue.getComplaintTiming()) {
                sortedList.add(issue);
            }
        }
        for (IssueDetails issue : rawList) {
            if (Constants.COMPLAINTS_TIMINGS.THIS_MONTH == issue.getComplaintTiming()) {
                sortedList.add(issue);
            }
        }
        for (IssueDetails issue : rawList) {
            if (Constants.COMPLAINTS_TIMINGS.PREVIOUS == issue.getComplaintTiming()) {
                sortedList.add(issue);
            }
        }
        return sortedList;
    }

    public static ArrayList<IssueDetails> filterComplaintsList(ArrayList<IssueDetails> rawList, String filter) {
        ArrayList<IssueDetails> filteredList = new ArrayList<>();
        for (IssueDetails issue : rawList) {
            if (issue.getReferenceNo().contains(filter)) {
                filteredList.add(issue);
            }
        }
        return sortComplaintsList(filteredList);
    }*/

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void logoutUser(final Activity act) {
        showProgressBarWithListener(act, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                DataManager.getInstance(act).clearData();
                Utils.showShortToast(act, "You have been successfully logged out !");
                act.startActivity(new Intent(act, AuthActivity.class));
                act.finish();
            }
        });
    }

    public static void showProgressBarWithListener(final Activity act, DialogInterface.OnCancelListener listener) {
        final ProgressDialog progress = new ProgressDialog(act);
        progress.setMessage("Please wait...");
        progress.show();
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progress.cancel();
            }
        };
        progress.setOnCancelListener(listener);
        Handler mHandler = new Handler();
        mHandler.postDelayed(progressRunnable, Constants.PROCESS_TIME);
    }

    /**
     * One Info the layout must contain the OK (R.id.ok) TextView
     *
     * @param ctx
     * @param layoutResource
     */
    public static void showSubmitDialog(Context ctx, int layoutResource) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(layoutResource, null);
        ((TextView) view.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * Converting the Date to required format
     *
     * @param currentDate
     * @return
     */
    public static String convertDate(String currentDate) {
        try {
            String currentDateFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(currentDateFormat, Locale.US);//set format of date you receiving from db
            Date date = sdf.parse(currentDate);
            SimpleDateFormat newDate = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);//set format of new date
            System.out.println(newDate.format(date));
            return newDate.format(date);
        } catch (ParseException e) {
            return currentDate;
        }
    }

    /**
     * Converting the Calender Date to required format
     *
     * @param calDate
     * @return
     */
    public static String convertDate(Calendar calDate) {
        long currentDateLong = calDate.getTimeInMillis();
        SimpleDateFormat newDate = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        return newDate.format(new Date(currentDateLong));
    }

    public static String getStatusString(String statusCode) {
        String status = "Received";
        if (Constants.COMPLAINT_STATUS.DP.equalsIgnoreCase(statusCode)) {
            status = "Dispatched";
        } else if (Constants.COMPLAINT_STATUS.AC.equalsIgnoreCase(statusCode)) {
            status = "Accepted";
        } else if (Constants.COMPLAINT_STATUS.EN.equalsIgnoreCase(statusCode)) {
            status = "En-Route";
        } else if (Constants.COMPLAINT_STATUS.AR.equalsIgnoreCase(statusCode)) {
            status = "Arrived";
        }
        return status;
    }

    public static String getAddressText(Context ctx, double latitude, double longitude) throws IOException {
        StringBuilder addressText = new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(ctx, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        String knownName = addresses.get(0).getFeatureName();
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        //Log.d("DEBUG_LOG", "> " + address + " < > " + city + " < > " + state + " < > " + country + " < > " + postalCode + " < > " + knownName);
        /*if (!TextUtils.isEmpty(knownName)) {
            addressText.append(knownName);
        }*/
        if (!TextUtils.isEmpty(address)) {
            //addressText.append(", ");
            addressText.append(address);
        }
        if (!TextUtils.isEmpty(city)) {
            addressText.append(", ");
            addressText.append(city);
        }
        if (!TextUtils.isEmpty(state)) {
            addressText.append(", ");
            addressText.append(state);
        }
        if (!TextUtils.isEmpty(country)) {
            addressText.append(", ");
            addressText.append(country);
        }
        if (!TextUtils.isEmpty(postalCode)) {
            addressText.append(", ");
            addressText.append(postalCode);
        }

        return addressText.toString();
    }
}
