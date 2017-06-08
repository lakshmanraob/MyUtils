package sheet.bottom.com.networklib.serviceLayer.apicalls;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintRequest;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintResponse;
import sheet.bottom.com.networklib.models.tecoutil.MyAuthResponse;

/**
 * Created by labattula on 30/05/17.
 */

public interface LoginUserApi {

    @GET("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<MyAuthResponse> authenticate();

    @POST("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<AddComplaintResponse> addComplaint(@Body Map<String, String> body);
}
