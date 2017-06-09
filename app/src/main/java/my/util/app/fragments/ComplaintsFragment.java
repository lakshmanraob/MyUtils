package my.util.app.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.adapter.ImagesAdapter;
import my.util.app.models.AddComplaintResponse;
import my.util.app.models.IssueDetails;
import my.util.app.models.PhotoDetails;
import my.util.app.network.global.MyLoaderResponse;
import my.util.app.network.loaders.AddComplaintLoader;
import my.util.app.service.FetchLocationAddress;
import my.util.app.utils.Constants;
import my.util.app.utils.ImageCaptureListener;
import my.util.app.utils.Utils;

public class ComplaintsFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageCaptureListener mImageCaptureListener;
    private ArrayList<PhotoDetails> images;
    private ImagesAdapter imagesAdapter;

    @BindView(R.id.outage_type)
    protected Spinner mOutageTypeSpinner;
    @BindView(R.id.photos_grid)
    protected GridView mGridView;
    @BindView(R.id.address)
    protected EditText mAddressField;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Resources resources;

    ComplaintReceiver receiver;

    LocationRequest mLocationRequest;

    public ComplaintsFragment() {
    }

    public static ComplaintsFragment newInstance(String param1, String param2) {
        ComplaintsFragment fragment = new ComplaintsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        resources = getResources();

        //Checking the CameraPermission
        if (isCameraPermissionsGiven(getContext())) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    Constants.CAMERA_REQUEST_CODE);
        }

        IntentFilter filter = new IntentFilter(ComplaintReceiver.COMPLAINT_LOCATION_ACTION);

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ComplaintReceiver();
        getActivity().registerReceiver(receiver, filter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_complaints, container, false);
        ButterKnife.bind(this, content);

        prepareListeners();

        images = new ArrayList<>();
        updateCaptureActionImage();

        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.outage_type));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOutageTypeSpinner.setAdapter(spinnerAdapter);

        imagesAdapter = new ImagesAdapter(getActivity(), images, mImageCaptureListener);
        mGridView.setAdapter(imagesAdapter);

        return content;
    }

    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @OnClick(R.id.call_button)
    protected void callEmergencyNumber(View v) {
        Utils.showEmergencyCallDialog(getContext());
    }

    @OnClick(R.id.location_detector)
    protected void fillCurrentLocation(View v) {

        //Checking the location permissions
        if (!isLocationPermissionGiven(getContext())) {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_PERMISSIONS_CODE);
        } else {
            if (mGoogleApiClient == null) {
                buildGoogleApiClient();
            } else {
                if (ActivityCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                }
            }
            if (mCurrentLocation != null) {
                startServiceForAddress(mCurrentLocation);
            } else {
                initLocationRequest();
                if (mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationCallback, null);
                }
            }
        }
    }

    @OnClick(R.id.submit_btn)
    protected void submitComplaint(View v) {
        String outageType = mOutageTypeSpinner.getSelectedItem().toString();
        if (!TextUtils.isEmpty(outageType) && !outageType.equalsIgnoreCase(resources.getString(R.string.outage_select))) {
            String address = mAddressField.getText().toString();
            if (!TextUtils.isEmpty(address) && address.length() > Constants.ADD_MIN_LENGTH) {
                if (mCurrentLocation != null) {
                    int referenceNumber = Utils.getRandom();
                    long dbStatus = DataManager.getInstance(getActivity()).getDbHelper().addNewComplaint(new IssueDetails(
                            outageType,
                            Calendar.getInstance(),
                            address,
                            Constants.COMPLAINT_STATUS.SUBMITTED,
                            mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude(),
                            referenceNumber));
                    if (dbStatus == -1) {
                        Log.d("DEBUG_LOG", "error " + resources.getString(R.string.error_database));
                        Utils.showShortToast(getActivity(), resources.getString(R.string.error_database));
                    } else {

                        Bundle bundle = new Bundle();
                        bundle.putString("user", DataManager.getInstance(getContext()).getUsername());
                        bundle.putString("password", DataManager.getInstance(getContext()).getPassword());
                        getLoaderManager().restartLoader(100, bundle, mAddComplaintCallbacks);
                    }
                } else {
                    Utils.showShortToast(getActivity(), resources.getString(R.string.error_location));
                }
            } else {
                Utils.showShortToast(getActivity(), resources.getString(R.string.error_address));
            }
        } else {
            Utils.showShortToast(getActivity(), resources.getString(R.string.error_outage_type));
        }
//        Utils.showSubmitDialog(getActivity());
        //lakshman addition will remove later
//        UtilDialog.showDialog(getActivity(), R.layout.submit_dialog, R.string.submit_message);
    }

//    private void showCallDialog() {
//        final Dialog dialog = new Dialog(getContext());
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.call_dialog, null);
//        view.findViewById(R.id.call_ok).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constants.EMERGENCY_NUMBER));
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.call_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(view);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//    }

    private void startServiceForAddress(Location location) {
        Intent msgIntent = new Intent(getContext(), FetchLocationAddress.class);
        msgIntent.putExtra(FetchLocationAddress.FETCH_LOCATION, location);
        getActivity().startService(msgIntent);
    }

    private void updateCaptureActionImage() {
        if (images != null) {
            for (PhotoDetails photo : images) {
                if (photo != null && photo.getImageName().equalsIgnoreCase(Constants.CAPTURE_IMAGE_NAME)) {
                    images.remove(photo);
                    break;
                }
            }
            images.add(images.size(), new PhotoDetails(Constants.CAPTURE_IMAGE_NAME, resources.getDrawable(R.drawable.take_photo, null)));
        }
        if (imagesAdapter != null) {
            mGridView.setNumColumns(images.size());
            imagesAdapter.notifyDataSetChanged();
        }
    }

    private void prepareListeners() {
        mImageCaptureListener = new ImageCaptureListener() {
            @Override
            public void onClick(PhotoDetails item) {
                if (item.getImageName().equalsIgnoreCase(Constants.CAPTURE_IMAGE_NAME)) {
                    startCameraForCapture();
                }
            }

            @Override
            public void onLongClick(PhotoDetails item) {
                if (!item.getImageName().equalsIgnoreCase(Constants.CAPTURE_IMAGE_NAME)) {
                    if (images != null) {
                        synchronized (images) {
                            Iterator<PhotoDetails> imageIterator = images.iterator();
                            while (imageIterator.hasNext()) {
                                PhotoDetails photoDetails = imageIterator.next();
                                if (photoDetails != null && photoDetails.getImageName().equalsIgnoreCase(item.getImageName())) {
                                    imageIterator.remove();
                                    updateCaptureActionImage();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };

    }

    /**
     * If the permission already given
     * True - if the permission already given
     * False - if not
     *
     * @param context
     * @return
     */
    private boolean isLocationPermissionGiven(Context context) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * If the permission laready given
     * True - if the permission is already given
     * False - if not
     *
     * @param context
     * @return
     */
    private boolean isCameraPermissionsGiven(Context context) {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    private void startCameraForCapture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST_CODE);
    }

    public void updateImages(Drawable imageCaptured) {
        //Utils.showShortToast(getActivity(), "Success");
        images.add(new PhotoDetails("image_seq_" + images.size() + 1, imageCaptured));
        updateCaptureActionImage();
        if (images.size() > Constants.IMAGE_COUNT) {
            images.remove(images.size() - 1);
        }
        mGridView.setNumColumns(images.size());
        imagesAdapter.notifyDataSetChanged();
    }

    /**
     * Updating the address field
     *
     * @param text
     */
    private void updateAddressField(String text) {
        mAddressField.setSingleLine(true);
        mAddressField.setMaxLines(1);
        mAddressField.setText(text);
    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                getAddressFromLocation();
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationCallback, null);
            }
        } else {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_PERMISSIONS_CODE);
        }
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult result) {
            super.onLocationResult(result);
            mCurrentLocation = result.getLastLocation();
            getAddressFromLocation();
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            if (!locationAvailability.isLocationAvailable() && mCurrentLocation == null) {
                Utils.showShortToast(getContext(), getString(R.string.error_location));
            }
        }
    };

    private void getAddressFromLocation() {
        String displayAddress = Utils.getCurrentAddress(getContext(), mCurrentLocation);
        updateAddressField(displayAddress);
        startServiceForAddress(mCurrentLocation);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        initLocationRequest();

    }

    private void initLocationRequest() {
        if (mLocationRequest == null) {
            //Location request creation
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationRequest.setSmallestDisplacement(Constants.LOCATION_UPDATE_REQUEST_IN_METERS);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.LOCATION_PERMISSIONS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }
                } else {
                    Utils.showShortToast(getContext(), "Location permission denied");
                }
                break;
            case Constants.CAMERA_PERMISSIONS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {

                    }
                } else {
                    Utils.showShortToast(getContext(), "Camera permission denied");
                }
                break;
        }
    }

    @OnClick(R.id.close)
    protected void closeScreen(View v) {
        ((BaseActivity) getActivity()).removeFragment(Constants.FRAGMENTS.BILL_DETAILS);
    }

    public class ComplaintReceiver extends BroadcastReceiver {

        public static final String COMPLAINT_LOCATION_ACTION =
                "my.util.app.fragment.ComplaintFragment.ComplaintReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String address = intent.getStringExtra(FetchLocationAddress.DETECTED_ADDRESS);
            Utils.showShortToast(context, address);
            updateAddressField(address);
        }
    }


    private LoaderManager.LoaderCallbacks<MyLoaderResponse<AddComplaintResponse>> mAddComplaintCallbacks =
            new LoaderManager.LoaderCallbacks<MyLoaderResponse<AddComplaintResponse>>() {

                @Override
                public Loader<MyLoaderResponse<AddComplaintResponse>> onCreateLoader(int loaderId, Bundle bundle) {
                    String userName = bundle.getString("user");
                    String password = bundle.getString("password");
                    return new AddComplaintLoader(getContext(), userName, password, DataManager.getInstance(getContext()).getUserCsrfToken(),
                            DataManager.getInstance(getContext()).getUserCookie1(), DataManager.getInstance(getContext()).getUserCookie2());
                }

                @Override
                public void onLoadFinished(Loader<MyLoaderResponse<AddComplaintResponse>> loader, MyLoaderResponse<AddComplaintResponse> loaderResult) {
                    Utils.showSubmitDialog(getActivity(), 1234567890);
                }

                @Override
                public void onLoaderReset(Loader<MyLoaderResponse<AddComplaintResponse>> loaderResult) {
                }
            };


}
