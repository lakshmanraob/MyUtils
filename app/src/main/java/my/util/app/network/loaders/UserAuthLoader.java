package my.util.app.network.loaders;

import android.content.Context;
import android.support.annotation.NonNull;

import my.util.app.models.LoginResponse;
import my.util.app.models.MyAuthResponse;
import my.util.app.network.HttpTaskLoader;
import my.util.app.network.UtilServiceLayer;
import my.util.app.network.global.MyLoaderException;
import my.util.app.network.global.MyLoaderResponse;
import my.util.app.utils.Constants;
import okhttp3.Credentials;

public class UserAuthLoader extends HttpTaskLoader<MyLoaderResponse<LoginResponse>> {

    String username;
    String password;

    public UserAuthLoader(@NonNull Context context, @NonNull String username, @NonNull String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected MyLoaderResponse<LoginResponse> loadDataInBackground() throws MyLoaderException {
        String authToken = Credentials.basic(Constants.USERNAME, Constants.PASSWORD);
        MyLoaderResponse<LoginResponse> resp = UtilServiceLayer.authenticate(authToken);
        return resp;

    }

    @Override
    protected MyLoaderResponse<LoginResponse> buildEmptyResult() {
        return null;
    }
}
