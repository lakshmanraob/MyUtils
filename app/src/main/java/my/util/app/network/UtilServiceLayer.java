package my.util.app.network;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import my.util.app.models.AccountDetailsResponse;
import my.util.app.models.AddComplaintResponse;
import my.util.app.models.LoginResponse;
import my.util.app.models.MyAuthResponse;
import retrofit2.Call;
import retrofit2.Response;
import my.util.app.network.global.MyLoaderException;
import my.util.app.network.global.MyLoaderResponse;
import my.util.app.network.apicalls.ApiCalls;
import my.util.app.network.myRetrofit.MyRetroFitLib;

public class UtilServiceLayer {


//    private static final String BASE_URL = "http://socwes1er46.solutions.glbsnet.com:8000/";
    private static final String BASE_URL = "https://desolate-cliffs-96575.herokuapp.com/";

    public static MyLoaderResponse<LoginResponse> authenticate(String authtoken) {
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");
        headersMap.put("X-CSRF-Token", "Fetch");

        Call<LoginResponse> authCall = MyRetroFitLib.getAuthRetrofit(BASE_URL, headersMap).create(ApiCalls.class).authenticate();
        return buildResponse(authCall);
    }

    public static MyLoaderResponse<MyAuthResponse> fetchComplaints(String authtoken) {
        Log.d("DEBUG_LOG", "fetchComplaints");
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");

        Call<MyAuthResponse> authCall = MyRetroFitLib.getAuthRetrofit(BASE_URL, headersMap).create(ApiCalls.class).fetchComplaints();
        return buildFetchComplaintsResponse(authCall);
    }

    public static MyLoaderResponse<AccountDetailsResponse> getAccountDetails(String authtoken) {
        Log.d("DEBUG_LOG", "getAccountDetails");
        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");

        Call<AccountDetailsResponse> authCall = MyRetroFitLib.getAuthRetrofit(BASE_URL, headersMap).create(ApiCalls.class).getAccountDetails();
        return buildAccountDetailsResponse(authCall);
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
        Call<AddComplaintResponse> addComplaintCall = MyRetroFitLib.addComplaint(BASE_URL, headersMap).create(ApiCalls.class).addComplaint(requestBody);
        return buildAddComplaintResponse(addComplaintCall);
    }

    public static MyLoaderResponse<LoginResponse> buildResponse(Call<LoginResponse> call) {
        MyLoaderResponse<LoginResponse> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                Log.d("DEBUG_LOG", "HEADERS > " + response.headers());
                Log.d("DEBUG_LOG", "BODY > " + new Gson().toJson(response.body()));
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

    public static MyLoaderResponse<MyAuthResponse> buildFetchComplaintsResponse(Call<MyAuthResponse> call) {
        Log.d("DEBUG_LOG", "buildFetchComplaintsResponse");
        MyLoaderResponse<MyAuthResponse> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<MyAuthResponse> response = call.execute();
            if (response.isSuccessful()) {
                Log.d("DEBUG_LOG", "HEADERS > " + response.headers());
                Log.d("DEBUG_LOG", "BODY > " + new Gson().toJson(response.body()));
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

    public static MyLoaderResponse<AccountDetailsResponse> buildAccountDetailsResponse(Call<AccountDetailsResponse> call) {
        Log.d("DEBUG_LOG", "buildAccountDetailsResponse");
        MyLoaderResponse<AccountDetailsResponse> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<AccountDetailsResponse> response = call.execute();
            if (response.isSuccessful()) {
                Log.d("DEBUG_LOG", "HEADERS > " + response.headers());
                Log.d("DEBUG_LOG", "BODY > " + new Gson().toJson(response.body()));
                returnValue.setData(response.body());
                returnValue.setHeaders(response.headers());
            } else {
                Log.d("DEBUG_LOG", "error");
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
