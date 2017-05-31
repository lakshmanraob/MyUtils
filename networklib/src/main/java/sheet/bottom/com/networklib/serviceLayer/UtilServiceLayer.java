package sheet.bottom.com.networklib.serviceLayer;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.Results;
import sheet.bottom.com.networklib.serviceLayer.apicalls.LoginUserApi;
import sheet.bottom.com.networklib.serviceLayer.myRetrofit.MyRetroFitLib;

/**
 * Created by labattula on 30/05/17.
 */

public class UtilServiceLayer {


    private static final String BASE_URL = "http://socwes1er46.solutions.glbsnet.com:8000/";

    public static MyLoaderResponse<Results> authenticate(String authtoken) {


        HashMap<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", authtoken);
        headersMap.put("Accept", "application/json");

        Call<Results> authCall = MyRetroFitLib.getAuthRetrofit(BASE_URL, headersMap).create(LoginUserApi.class).authenticate();

        return buildResponse(authCall);
    }

    public static MyLoaderResponse<Results> buildResponse(Call<Results> call) {
        MyLoaderResponse<Results> returnValue = new MyLoaderResponse<>();
        MyLoaderException.Builder errorBuilder = new MyLoaderException.Builder();

        try {
            Response<Results> response = call.execute();
            if (response.isSuccessful()) {
                returnValue.setData(response.body());
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

}
