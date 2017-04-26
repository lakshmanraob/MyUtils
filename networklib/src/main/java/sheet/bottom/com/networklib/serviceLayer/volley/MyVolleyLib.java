package sheet.bottom.com.networklib.serviceLayer.volley;

import android.content.Context;

import com.android.volley.Response;

import java.util.HashMap;

import sheet.bottom.com.networklib.models.stackexchange.StackResponse;

/**
 * Created by labattula on 23/09/16.
 */

public class MyVolleyLib {

    public static VolleyController getInstance(Context context){
        return VolleyController.getInstance(context);
    }

    public static StackOverFlowGETRequest getStackRequest(String url, Response.Listener listener, HashMap<String, String> map, Response.ErrorListener errorListener) {
        StackOverFlowGETRequest request = new StackOverFlowGETRequest(url, StackResponse.class, listener, map, errorListener);
        return request;
    }

}
