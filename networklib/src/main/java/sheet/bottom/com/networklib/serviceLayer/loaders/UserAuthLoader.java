package sheet.bottom.com.networklib.serviceLayer.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.Credentials;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.LoginResult;
import sheet.bottom.com.networklib.models.tecoutil.MyAuthResponse;
import sheet.bottom.com.networklib.models.tecoutil.Results;
import sheet.bottom.com.networklib.serviceLayer.HttpTaskLoader;
import sheet.bottom.com.networklib.serviceLayer.UtilServiceLayer;

public class UserAuthLoader extends HttpTaskLoader<MyLoaderResponse<MyAuthResponse>> {

    String username;
    String password;

    public UserAuthLoader(@NonNull Context context, @NonNull String username, @NonNull String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected MyLoaderResponse<MyAuthResponse> loadDataInBackground() throws MyLoaderException {
        String authToken = Credentials.basic(username, password);
        MyLoaderResponse<MyAuthResponse> resp = UtilServiceLayer.authenticate(authToken);
        return resp;

    }

    @Override
    protected MyLoaderResponse<MyAuthResponse> buildEmptyResult() {
        return null;
    }
}
