package my.util.app.network.apicalls;

import java.util.Map;

import my.util.app.models.AddComplaintResponse;
import my.util.app.models.MyAuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginUserApi {

    @GET("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<MyAuthResponse> authenticate();

    @POST("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<AddComplaintResponse> addComplaint(@Body Map<String, String> body);
}
