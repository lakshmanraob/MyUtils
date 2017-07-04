package my.util.app.network.apicalls;

import java.util.Map;

import my.util.app.models.AccountDetailsResponse;
import my.util.app.models.AddComplaintResponse;
import my.util.app.models.LoginResponse;
import my.util.app.models.MyAuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCalls {

    @GET("sap/opu/odata/sap/ZUTIL_APP_SRV/NOTIF_AUTHSet(Bpart='1000000014',SsnPwd='0000')?$format=json")
    Call<LoginResponse> authenticate();

    @GET("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<MyAuthResponse> fetchComplaints();

    @POST("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_mainSet")
    Call<AddComplaintResponse> addComplaint(@Body Map<String, String> body);

//    @POST("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_accountSet(Bpart='1000000014')?$format=json")
    @GET("sap/opu/odata/sap/ZUTIL_APP_SRV/Notif_accountSet?$format=json")
    Call<AccountDetailsResponse> getAccountDetails();
}
