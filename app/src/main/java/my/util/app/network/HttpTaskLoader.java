package my.util.app.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import my.util.app.network.global.MyLoaderException;

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
     * @throws MyLoaderException
     */
    protected abstract T loadDataInBackground() throws MyLoaderException;

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
