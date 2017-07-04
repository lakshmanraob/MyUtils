package my.util.app.network.loaders;

import android.content.Context;
import android.support.annotation.NonNull;

import my.util.app.models.AccountDetailsResponse;
import my.util.app.network.HttpTaskLoader;
import my.util.app.network.UtilServiceLayer;
import my.util.app.network.global.MyLoaderException;
import my.util.app.network.global.MyLoaderResponse;
import okhttp3.Credentials;

public class AccountDetailsLoader extends HttpTaskLoader<MyLoaderResponse<AccountDetailsResponse>> {

    String username;
    String password;

    public AccountDetailsLoader(@NonNull Context context, @NonNull String username, @NonNull String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected MyLoaderResponse<AccountDetailsResponse> loadDataInBackground() throws MyLoaderException {
        String authToken = Credentials.basic(username, password);
        MyLoaderResponse<AccountDetailsResponse> resp = UtilServiceLayer.getAccountDetails(authToken);
        return resp;

    }

    @Override
    protected MyLoaderResponse<AccountDetailsResponse> buildEmptyResult() {
        return null;
    }
}
