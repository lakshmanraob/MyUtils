package my.util.app.network.loaders;

import android.content.Context;
import android.support.annotation.NonNull;

import my.util.app.models.AddComplaintResponse;
import my.util.app.network.HttpTaskLoader;
import my.util.app.network.UtilServiceLayer;
import my.util.app.network.global.MyLoaderException;
import my.util.app.network.global.MyLoaderResponse;
import okhttp3.Credentials;

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
        String authToken = Credentials.basic(username, password);
        MyLoaderResponse<AddComplaintResponse> resp = UtilServiceLayer.addComplaint(authToken, token, cookie1, cookie2);
        return resp;

    }

    @Override
    protected MyLoaderResponse<AddComplaintResponse> buildEmptyResult() {
        return null;
    }
}
