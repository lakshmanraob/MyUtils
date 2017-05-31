package sheet.bottom.com.networklib.serviceLayer;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.D;
import sheet.bottom.com.networklib.models.tecoutil.Results;
import sheet.bottom.com.networklib.serviceLayer.apicalls.LoginUserApi;
import sheet.bottom.com.networklib.serviceLayer.myRetrofit.MyRetroFitLib;

/**
 * Created by labattula on 30/05/17.
 */

public class UtilServiceLayer {


    private static final String BASE_URL = "http://socwes1er46.solutions.glbsnet.com:8000/";

    public static MyLoaderResponse<D> authenticate(String authtoken) {


        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");

        Call<D> authCall = MyRetroFitLib.getAuthRetrofit(BASE_URL, headersMap).create(LoginUserApi.class).authenticate();

        return buildResponse(authCall);
    }

    public static MyLoaderResponse<D> buildResponse(Call<D> call) {
        MyLoaderResponse<D> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<D> response = call.execute();
            if (response.isSuccessful()) {
                Log.d("DEBUG_LOG", "is SUCCESS ******************************");
//                Log.d("DEBUG_LOG", "response > " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                logLargeString(new GsonBuilder().setPrettyPrinting().create().toJson(response));
                returnValue.setData(response.body());
            } else {
                Log.d("DEBUG_LOG", "FAIL ******************************");
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


    public static void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.d("DEBUG_LOG", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.d("DEBUG_LOG", str);
        }
    }

}
