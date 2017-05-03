package my.util.app.utils;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import my.util.app.R;

public class Utils {
    private static ProgressDialog mProgressDialog;

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
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ctx);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
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

    public static void showSubmitDialog(Context ctx) {
        final Dialog dialog = new Dialog(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.submit_dialog, null);
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

}
