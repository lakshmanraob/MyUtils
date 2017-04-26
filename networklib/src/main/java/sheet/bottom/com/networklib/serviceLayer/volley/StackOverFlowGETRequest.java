package sheet.bottom.com.networklib.serviceLayer.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by labattula on 23/09/16.
 */

public class StackOverFlowGETRequest<T> extends Request<T> {

    private final Gson mGson = new Gson();
    private final Class<T> modelClass;
    private final Response.Listener<T> listener;

    private HashMap<String, String> mHeaders = new HashMap<>();

    public StackOverFlowGETRequest(String url, Class<T> modelClass, Response.Listener<T> listener,
                                   Map<String, String> headers, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.modelClass = modelClass;
        this.listener = listener;
        mHeaders.putAll(headers);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            /**
             *  creating the json String
             */
            String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            /**
             * Parsing the String using GSON
             */
            return Response.success(mGson.fromJson(jsonStr, modelClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

}
