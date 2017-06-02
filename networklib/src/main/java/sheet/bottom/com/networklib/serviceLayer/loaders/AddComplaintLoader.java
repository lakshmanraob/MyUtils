package sheet.bottom.com.networklib.serviceLayer.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.Credentials;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintResponse;
import sheet.bottom.com.networklib.models.tecoutil.AddComplaintResponse;
import sheet.bottom.com.networklib.serviceLayer.HttpTaskLoader;
import sheet.bottom.com.networklib.serviceLayer.UtilServiceLayer;

/**
 * Created by labattula on 30/05/17.
 */

public class AddComplaintLoader extends HttpTaskLoader<MyLoaderResponse<AddComplaintResponse>> {

    String username;
    String password;
    String token;

    public AddComplaintLoader(@NonNull Context context, @NonNull String username, @NonNull String password, @NonNull String token) {
        super(context);
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    protected MyLoaderResponse<AddComplaintResponse> loadDataInBackground() throws MyLoaderException {
        Log.d("DEBUG_LOG", "******** AddComplaintLoader ********");
        String authToken = Credentials.basic(username, password);
        MyLoaderResponse<AddComplaintResponse> resp = UtilServiceLayer.addComplaint(authToken, token);
        Log.d("DEBUG_LOG", "resp " + ((resp.toString() == null) ? "is null" : "NOT null : " + resp.toString()));
        Log.d("DEBUG_LOG", "****************");
        return resp;

    }

    @Override
    protected MyLoaderResponse<AddComplaintResponse> buildEmptyResult() {
        return null;
    }
}
