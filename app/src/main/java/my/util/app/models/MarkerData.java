package my.util.app.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by labattula on 17/05/17.
 */

public class MarkerData {

    LatLng markerLatLng;
    int outageType;

    public LatLng getMarkerLatLng() {
        return markerLatLng;
    }

    public void setMarkerLatLng(LatLng markerLatLng) {
        this.markerLatLng = markerLatLng;
    }

    public int getOutageType() {
        return outageType;
    }

    public void setOutageType(int outageType) {
        this.outageType = outageType;
    }
}
