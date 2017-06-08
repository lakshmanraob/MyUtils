package sheet.bottom.com.networklib.serviceLayer.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.Credentials;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintResponse;
import sheet.bottom.com.networklib.serviceLayer.HttpTaskLoader;
import sheet.bottom.com.networklib.serviceLayer.UtilServiceLayer;

public class AddComplaintLoader extends HttpTaskLoader<MyLoaderResponse<AddComplaintResponse>> {

    String username;
    String password;
    String token;
    String cookie1;
    String cookie2;

    public AddComplaintLoader(@NonNull Context context, @NonNull String username, @NonNull String password, @NonNull String token, @NonNull String cookie1, @NonNull String cookie2) {
        super(context);
        this.username = username;
        this.password = password;
        this.token = token;
        this.cookie1 = cookie1;
        this.cookie2 = cookie2;
    }

    @Override
    protected MyLoaderResponse<AddComplaintResponse> loadDataInBackground() throws MyLoaderException {
        Log.d("DEBUG_LOG", "******** AddComplaintLoader ********");
        String authToken = Credentials.basic(username, password);
        MyLoaderResponse<AddComplaintResponse> resp = UtilServiceLayer.addComplaint(authToken, token, cookie1, cookie2);
        Log.d("DEBUG_LOG", "resp " + ((resp.toString() == null) ? "is null" : "NOT null : " + resp.toString()));
        Log.d("DEBUG_LOG", "****************");
        return resp;

    }

    @Override
    protected MyLoaderResponse<AddComplaintResponse> buildEmptyResult() {
        return null;
    }
}
