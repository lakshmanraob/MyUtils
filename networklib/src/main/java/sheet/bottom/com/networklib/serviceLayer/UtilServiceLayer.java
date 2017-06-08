package sheet.bottom.com.networklib.serviceLayer;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Header;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintRequest;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintResponse;
import sheet.bottom.com.networklib.models.tecoutil.DJavaClass;
import sheet.bottom.com.networklib.models.tecoutil.MyAuthResponse;
import sheet.bottom.com.networklib.serviceLayer.apicalls.LoginUserApi;
import sheet.bottom.com.networklib.serviceLayer.myRetrofit.MyRetroFitLib;

public class UtilServiceLayer {


    private static final String BASE_URL = "http://socwes1er46.solutions.glbsnet.com:8000/";

    public static MyLoaderResponse<MyAuthResponse> authenticate(String authtoken) {
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");
        headersMap.put("X-CSRF-Token", "Fetch");

        Call<MyAuthResponse> authCall = MyRetroFitLib.getAuthRetrofit(BASE_URL, headersMap).create(LoginUserApi.class).authenticate();
        return buildResponse(authCall);
    }

    public static MyLoaderResponse<AddComplaintResponse> addComplaint(String authtoken, String csrfToken, String cookie1, String cookie2) {
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");
        headersMap.put("Content-type", "application/json");
        headersMap.put("X-CSRF-Token", csrfToken);
        headersMap.put("Cookie", cookie2 + " " + cookie1);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("Bpart", ""); // hardcoded
        Call<AddComplaintResponse> addComplaintCall = MyRetroFitLib.addComplaint(BASE_URL, headersMap).create(LoginUserApi.class).addComplaint(requestBody);
        return buildAddComplaintResponse(addComplaintCall);
    }

    public static MyLoaderResponse<MyAuthResponse> buildResponse(Call<MyAuthResponse> call) {
        MyLoaderResponse<MyAuthResponse> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<MyAuthResponse> response = call.execute();
            if (response.isSuccessful()) {
                returnValue.setData(response.body());
                returnValue.setHeaders(response.headers());
            } else {
                returnValue.setData(null);
                errorBuilder.setErrorCode(101);
                errorBuilder.setDetailMessage("Request Failure");
                returnValue.setException(errorBuilder.build());
            }
        } catch (IOException e) {
            errorBuilder.setErrorCode(101);
            errorBuilder.setDetailMessage("IOException");
        } catch (Exception e) {
            errorBuilder.setErrorCode(101);
            errorBuilder.setDetailMessage("SomeOtherException");
        }
        return returnValue;
    }

    public static MyLoaderResponse<AddComplaintResponse> buildAddComplaintResponse(Call<AddComplaintResponse> call) {
        MyLoaderResponse<AddComplaintResponse> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<AddComplaintResponse> response = call.execute();
            if (response.isSuccessful()) {
                returnValue.setData(response.body());
                returnValue.setHeaders(response.headers());
            } else {
                returnValue.setData(null);
                errorBuilder.setErrorCode(101);
                errorBuilder.setDetailMessage("Request Failure");
                returnValue.setException(errorBuilder.build());
            }
        } catch (IOException e) {
            Log.d("DEBUG_LOG", "FAIL IOException * " + e.getMessage());
            errorBuilder.setErrorCode(101);
            errorBuilder.setDetailMessage("IOException");
        } catch (Exception e) {
            Log.d("DEBUG_LOG", "FAIL Exception * " + e.getMessage());
            errorBuilder.setErrorCode(101);
            errorBuilder.setDetailMessage("SomeOtherException");
        }
        return returnValue;
    }

}
