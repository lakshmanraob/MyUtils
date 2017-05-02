package my.util.app.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.adapter.ImagesAdapter;
import my.util.app.utils.Constants;
import my.util.app.utils.ImageCaptureListener;
import my.util.app.utils.PhotoDetails;
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

        //Checking the CameraPermission
        if (isCameraPermissionsGiven(getContext())) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    Constants.CAMERA_REQUEST_CODE);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_complaints, container, false);
        ButterKnife.bind(this, content);

        prepareListeners();

        images = new ArrayList<>();
        addCaptureActionImage();

        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.outage_type));
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
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constants.EMERGENCY_NUMBER));
        startActivity(intent);
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
            if (mCurrentLocation != null) {
                String address = Utils.getCurrentAddress(getActivity(), mCurrentLocation);
                updateAddressField(address);
            }
        }

    }

    @OnClick(R.id.submit_btn)
    protected void submitComplaint(View v) {
        Utils.showSubmitDialog(getActivity());
    }

    private void addCaptureActionImage() {
        Resources res = getActivity().getResources();
        if (images != null) {
            boolean hasCameraImage = false;
            for (PhotoDetails photo : images) {
                if (photo != null && photo.getImageName().equalsIgnoreCase(Constants.CAPTURE_IMAGE_NAME)) {
                    hasCameraImage = true;
                    break;
                }
            }
            if (!hasCameraImage) {
                images.add(0, new PhotoDetails(Constants.CAPTURE_IMAGE_NAME, res.getDrawable(R.drawable.take_photo, null)));
            }
        }
        if (imagesAdapter != null) {
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
                                    addCaptureActionImage();
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
        Utils.showShortToast(getActivity(), "Success");
        images.add(new PhotoDetails("image_seq_" + images.size() + 1, imageCaptured));
        if (images.size() > Constants.IMAGE_COUNT) {
            images.remove(0);
        }
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
                String displayAddress = Utils.getCurrentAddress(getContext(), mCurrentLocation);
                Utils.showShortToast(getContext(), displayAddress);
                updateAddressField(displayAddress);
            }
        } else {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_PERMISSIONS_CODE);
        }
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
}
