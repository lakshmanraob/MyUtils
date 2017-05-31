package sheet.bottom.com.networklib.serviceLayer.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.Credentials;
import sheet.bottom.com.networklib.models.global.MyLoaderException;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.D;
import sheet.bottom.com.networklib.models.tecoutil.Results;
import sheet.bottom.com.networklib.serviceLayer.HttpTaskLoader;
import sheet.bottom.com.networklib.serviceLayer.UtilServiceLayer;

/**
 * Created by labattula on 30/05/17.
 */

public class UserAuthLoader extends HttpTaskLoader<MyLoaderResponse<D>> {

    String username;
    String password;

    public UserAuthLoader(@NonNull Context context, @NonNull String username, @NonNull String password) {
        super(context);
        this.username = username;
        this.password = password;
    }

    @Override
    protected MyLoaderResponse<D> loadDataInBackground() throws MyLoaderException {
        String authToken = Credentials.basic(username, password);
        MyLoaderResponse<D> resp = UtilServiceLayer.authenticate(authToken);
        Log.d("DEBUG_LOG", "****************");
        Log.d("DEBUG_LOG", "resp " + ((resp.toString() == null) ? "is null" : "NOT null : " + resp.toString()));
        Log.d("DEBUG_LOG", "resp.getData " + ((resp.getData() == null) ? "is null" : "NOT null : " + resp.getData()));
        Results resultsData= ((D)resp.getData()).getResults();
        Log.d("DEBUG_LOG", "resultsData length " + ((resultsData == null) ? "is null" : "NOT null : " + resultsData.getResults().length));
        Log.d("DEBUG_LOG", "****************");
        return resp;

    }

    @Override
    protected MyLoaderResponse<D> buildEmptyResult() {
        return null;
    }
}
