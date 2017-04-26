package sheet.bottom.com.networklib.serviceLayer;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import sheet.bottom.com.networklib.models.global.StLoaderException;

/**
 * Created by labattula on 22/09/16.
 */

public abstract class HttpTaskLoader<T> extends AsyncTaskLoader<T> {

    private static final String TAG = HttpTaskLoader.class.getSimpleName();

    private T result;

    public HttpTaskLoader(Context context) {
        super(context);
    }


    public final T loadInBackground() {
        try {
            return this.loadDataInBackground();
        } catch (Exception e) {
            Log.e(TAG, String.format("Error encountered with message: %s", e.getMessage()));
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Unexpected Exception", e);
            }
            return this.buildEmptyResult();
        }
    }

    public void deliverResult(T data) {
        this.result = data;
        if (this.result == null) {
            this.result = this.buildEmptyResult();
        }

        if (this.isStarted()) {
            super.deliverResult(this.result);
        }
    }

    /**
     * Loading the Data in BackGround
     * @return
     * @throws StLoaderException
     */
    protected abstract T loadDataInBackground() throws StLoaderException;

    /**
     * Constructing the EmptyResults
     * @return
     */
    protected abstract T buildEmptyResult();

    /**
     * Start loading the Results
     */
    protected void onStartLoading() {
        if (this.result != null) {
            this.deliverResult(this.result);
        } else {
            this.forceLoad();
        }
    }

    /**
     * Resetting the Loader
     */
    protected void onReset() {
        super.onReset();
        this.onStopLoading();
        this.result = null;
    }

    /**
     * Stopping the Loader
     */
    protected void onStopLoading() {
        this.cancelLoad();
    }
}
