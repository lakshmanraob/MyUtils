package sheet.bottom.com.networklib.serviceLayer.myRetrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetroFitLib {

    private static OkHttpClient mobileEndpointClient;
    private static OkHttpClient mobileEndpointClientForComplaints;

    public static Retrofit getAuthRetrofit(String baseUrl, HashMap<String, String> headerMap) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        GsonConverterFactory lenientFactory = GsonConverterFactory.create(gson);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(lenientFactory)
                .client(getMobileEndpointClient(headerMap))
                .build();
    }

    public static Retrofit addComplaint(String baseUrl, HashMap<String, String> headerMap) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        GsonConverterFactory lenientFactory = GsonConverterFactory.create(gson);
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(lenientFactory)
                .client(getMobileEndpointClientForComplaints(headerMap))
                .build();
    }


    /**
     * headerMap for authentication
     *
     * @return a client configured for authenticated HTTP requests
     */
    private static OkHttpClient getMobileEndpointClient(HashMap<String, String> headerMap) {
        OkHttpClient.Builder clientBuilder = null;
        if (mobileEndpointClient == null) {
            clientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderRequestInterceptor(headerMap));
            mobileEndpointClient = clientBuilder.build();
        }
        return mobileEndpointClient;
    }

    /**
     * headerMap for add complaint
     *
     * @return a client configured for authenticated HTTP requests
     */
    private static OkHttpClient getMobileEndpointClientForComplaints(HashMap<String, String> headerMap) {
        Log.d("DEBUG_LOG", "getMobileEndpointClientForComplaints");
        OkHttpClient.Builder clientBuilder = null;
        if (mobileEndpointClientForComplaints == null) {
            clientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderRequestInterceptor(headerMap));
            mobileEndpointClientForComplaints = clientBuilder.build();
        }
        return mobileEndpointClientForComplaints;
    }

    private static class HeaderRequestInterceptor implements Interceptor {

        HashMap<String, String> headerMap;

        public HeaderRequestInterceptor(HashMap<String, String> headerMap) {
            this.headerMap = headerMap;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder newRequest = request.newBuilder();
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                newRequest.addHeader(entry.getKey(), entry.getValue());
            }
            return chain.proceed(newRequest.build());
        }
    }

}
