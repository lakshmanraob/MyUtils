package sheet.bottom.com.networklib.serviceLayer.apicalls;

import retrofit2.Call;
import retrofit2.http.GET;
import sheet.bottom.com.networklib.models.tecoutil.MyAuthResponse;

/**
 * Created by labattula on 30/05/17.
 */

public interface LoginUserApi {

    @GET("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<MyAuthResponse> authenticate();
}
