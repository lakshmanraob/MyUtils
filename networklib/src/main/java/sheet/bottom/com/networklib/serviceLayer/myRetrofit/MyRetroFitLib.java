package sheet.bottom.com.networklib.serviceLayer.myRetrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sheet.bottom.com.networklib.models.global.StLoaderException;
import sheet.bottom.com.networklib.models.global.StLoaderResponse;
import sheet.bottom.com.networklib.models.stackexchange.StackResponse;

/**
 * Created by labattula on 23/09/16.
 */

public class MyRetroFitLib {

    public static Retrofit getAuthRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        GsonConverterFactory lenientFactory = GsonConverterFactory.create(gson);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(lenientFactory)
                .build();
    }

    public static StLoaderResponse<StackResponse> buildReponse(Call<StackResponse> call) {
        StLoaderResponse<StackResponse> returnValue = new StLoaderResponse<>();
        StLoaderException.Builder errorBuilder = new StLoaderException.Builder();

        try {
            Response<StackResponse> response = call.execute();
            if (response.isSuccessful()) {
                returnValue.setData(response.body());
            } else {
                returnValue.setData(null);
                errorBuilder.setErrorCode(101);
                errorBuilder.setDetailMessage("Request Failure");
                returnValue.setException(errorBuilder.build());

            }
        }catch (IOException e){
            errorBuilder.setErrorCode(101);
            errorBuilder.setDetailMessage("IOException");
        }
        return returnValue;
    }

}
