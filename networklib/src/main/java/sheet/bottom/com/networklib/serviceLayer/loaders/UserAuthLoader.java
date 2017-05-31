package sheet.bottom.com.networklib.serviceLayer.loaders;

import android.content.Context;
import android.support.annotation.NonNull;

import okhttp3.Credentials;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.Results;
import sheet.bottom.com.networklib.serviceLayer.HttpTaskLoader;
import sheet.bottom.com.networklib.serviceLayer.UtilServiceLayer;

/**
 * Created by labattula on 30/05/17.
 */

public class UserAuthLoader extends HttpTaskLoader<MyLoaderResponse<Results>> {

    String username;
    String password;

    public UserAuthLoader(@NonNull Context context, @NonNull String username, @NonNull String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected MyLoaderResponse<Results> loadDataInBackground() throws MyLoaderException {
        String authToken = Credentials.basic(username, password);
        return UtilServiceLayer.authenticate(authToken);

    }

    @Override
    protected MyLoaderResponse<Results> buildEmptyResult() {
        return null;
    }
}
