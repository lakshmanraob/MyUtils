package my.util.app.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

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

public class ComplaintsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageCaptureListener mImageCaptureListener;
    private ArrayList<PhotoDetails> images;
    private ImagesAdapter imagesAdapter;
    private Location currentLocation;
    private LocationListener mLocationListener;

    @BindView(R.id.outage_type)
    protected Spinner mOutageTypeSpinner;
    @BindView(R.id.photos_grid)
    protected GridView mGridView;
    @BindView(R.id.address)
    protected EditText mAddressField;

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
    }

    @OnClick(R.id.call_button)
    protected void callEmergencyNumber(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constants.EMERGENCY_NUMBER));
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_complaints, container, false);
        ButterKnife.bind(this, content);

        prepareListeners();
        Utils.startGPS(getActivity(), mLocationListener);

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

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                fillCurrentLocation(null);
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
        };
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

    @OnClick(R.id.location_detector)
    protected void fillCurrentLocation(View v) {
        if (currentLocation != null) {
            String displayAddress = Utils.getCurrentAddress(getActivity(), currentLocation);
            if (!TextUtils.isEmpty(displayAddress)) {
                mAddressField.setText("");
                mAddressField.setText(displayAddress);
            } else {
                Utils.showShortToast(getActivity(), "Current location unavailable.");
                Utils.startGPS(getActivity(), mLocationListener);
            }
        } else {
            Utils.showShortToast(getActivity(), "Current location unavailable.");
            Utils.startGPS(getActivity(), mLocationListener);
        }
    }

    @OnClick(R.id.submit_btn)
    protected void submitComplaint(View v) {
        Utils.showSubmitDialog(getActivity());
    }
}
