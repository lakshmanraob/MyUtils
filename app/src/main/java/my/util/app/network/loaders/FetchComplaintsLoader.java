package my.util.app.network.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import my.util.app.models.LoginResponse;
import my.util.app.models.MyAuthResponse;
import my.util.app.network.HttpTaskLoader;
import my.util.app.network.UtilServiceLayer;
import my.util.app.network.global.MyLoaderException;
import my.util.app.network.global.MyLoaderResponse;
import my.util.app.utils.Constants;
import okhttp3.Credentials;

public class FetchComplaintsLoader extends HttpTaskLoader<MyLoaderResponse<MyAuthResponse>> {

    String username;
    String password;

    public FetchComplaintsLoader(@NonNull Context context, @NonNull String username, @NonNull String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected MyLoaderResponse<MyAuthResponse> loadDataInBackground() throws MyLoaderException {
        Log.d("DEBUG_LOG", "loadDataInBackground");
        String authToken = Credentials.basic(Constants.USERNAME, Constants.PASSWORD);
        MyLoaderResponse<MyAuthResponse> resp = UtilServiceLayer.fetchComplaints(authToken);
        return resp;

    }

    @Override
    protected MyLoaderResponse<MyAuthResponse> buildEmptyResult() {
        return null;
    }
}
