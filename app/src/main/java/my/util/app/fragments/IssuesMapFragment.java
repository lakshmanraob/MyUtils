package my.util.app.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.OutageView;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.models.MarkerData;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

/**
 * Created by labattula on 16/05/17.
 */

public class IssuesMapFragment extends SupportMapFragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    @BindView(R.id.power_outage_select)
    OutageView powerOutageView;
    @BindView(R.id.street_outage_select)
    OutageView streetOutageView;
    @BindView(R.id.safety_outage_select)
    OutageView safetyOutageView;

    private ArrayList<MarkerData> myOutageListData = new ArrayList<>();

    public static IssuesMapFragment newInstance(String param1, String param2) {
        IssuesMapFragment fragment = new IssuesMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_issues_map,
                container, false);
        ButterKnife.bind(this, view);

        //building the Google Apiclient
        buildGoogleApiClient();

        getMapAsync(this);

        View mapView = super.onCreateView(inflater, container, savedInstanceState);

        if (mapView != null) {
            ((ViewGroup) view.findViewById(R.id.store_locator_map_fragment_container)).addView(mapView);
        }

        return view;
    }

    @OnClick(R.id.power_outage_select)
    protected void powerOutageSelect(View v) {
        powerOutageView.setSelected(!powerOutageView.isSelected());
        mMap.clear();
        showLocationInMap();
    }

    @OnClick(R.id.street_outage_select)
    protected void streetOutageSelect(View v) {
        streetOutageView.setSelected(!streetOutageView.isSelected());
        mMap.clear();
        showLocationInMap();
    }

    @OnClick(R.id.safety_outage_select)
    protected void safetyOutageSelect(View v) {
        safetyOutageView.setSelected(!safetyOutageView.isSelected());
        mMap.clear();
        showLocationInMap();
    }

    @OnClick(R.id.issues_map_close)
    protected void closeScreen(View v) {
        ((BaseActivity) getActivity()).removeFragment(Constants.FRAGMENTS.BILL_DETAILS);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            if (mCurrentLocation != null) {
                //move the camera to the current location
                LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13));
            }
        } else {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.LOCATION_PERMISSIONS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Utils.showShortToast(getContext(), "Location permission denied");
                }
                break;
        }
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
                showLocationInMap();
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
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mCurrentLocation = locationResult.getLastLocation();
            showLocationInMap();
            getRandomLocation(1000);
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            if (!locationAvailability.isLocationAvailable() && mCurrentLocation == null) {
                Utils.showShortToast(getContext(), getString(R.string.error_location));
            }
        }
    };

    /**
     * Getting the random location basing on the radius around the current location
     *
     * @param radius
     * @return
     */
    public ArrayList<MarkerData> getRandomLocation(int radius) {

        LatLng point = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        ArrayList<MarkerData> randomMarkerData = new ArrayList<>();
        Location myLocation = new Location("");
        myLocation.setLatitude(point.latitude);
        myLocation.setLongitude(point.longitude);

        //This is to generate 10 random points
        for (int i = 0; i < 20; i++) {
            double x0 = point.latitude;
            double y0 = point.longitude;

            Random random = new Random();

            // Convert radius from meters to degrees
            double radiusInDegrees = radius / 111000f;

            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(y0);

            double foundLatitude = new_x + x0;
            double foundLongitude = y + y0;
            LatLng randomLatLng = new LatLng(foundLatitude, foundLongitude);
            MarkerData markerData = new MarkerData();
            markerData.setMarkerLatLng(randomLatLng);
            markerData.setOutageType(i % 3);

            randomMarkerData.add(markerData);

        }
        return randomMarkerData;
    }

    private void showLocationInMap() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (MarkerData data : getRandomLocation(100)) {
                switch (data.getOutageType()) {
                    case Constants.OUTAGE_TYPE.POWER_OUTAGE:
                        if (powerOutageView.isSelected()) {
                            mMap.addMarker(new MarkerOptions()
                                    .icon(getPowerOutageImageRes(data))
                                    .position(data.getMarkerLatLng()));

                            builder.include(data.getMarkerLatLng());
                        }
                        break;
                    case Constants.OUTAGE_TYPE.OTHER_OUTAGE:
                        // nothing doing
                        break;
                    case Constants.OUTAGE_TYPE.STREET_LIGHT_OUTAGE:
                        if (streetOutageView.isSelected()) {
                            mMap.addMarker(new MarkerOptions()
                                    .icon(getPowerOutageImageRes(data))
                                    .position(data.getMarkerLatLng()));

                            builder.include(data.getMarkerLatLng());
                        }
                        break;
                    case Constants.OUTAGE_TYPE.SAFETY_CONCERN:
                        if (safetyOutageView.isSelected()) {
                            mMap.addMarker(new MarkerOptions()
                                    .icon(getPowerOutageImageRes(data))
                                    .position(data.getMarkerLatLng()));

                            builder.include(data.getMarkerLatLng());
                        }
                        break;
                }

            }
            if (mCurrentLocation != null) {
                builder.include(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
            }
            LatLngBounds bounds = builder.build();

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            mMap.animateCamera(cu);

        }
    }

    private BitmapDescriptor getPowerOutageImageRes(MarkerData data) {
        switch (data.getOutageType()) {
            case Constants.OUTAGE_TYPE.POWER_OUTAGE:
                return BitmapDescriptorFactory.fromResource(R.mipmap.powerred);
            case Constants.OUTAGE_TYPE.SAFETY_CONCERN:
                return BitmapDescriptorFactory.fromResource(R.mipmap.safetyred);
            case Constants.OUTAGE_TYPE.STREET_LIGHT_OUTAGE:
                return BitmapDescriptorFactory.fromResource(R.mipmap.streetred);
            case Constants.OUTAGE_TYPE.OTHER_OUTAGE:
            default:
                return BitmapDescriptorFactory.fromResource(R.mipmap.powerred);

        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Utils.showShortToast(getContext(), getString(R.string.error_location));
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

}
