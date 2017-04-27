package my.util.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

import butterknife.ButterKnife;
import my.util.app.utils.LruBitmapCache;

/**
 * Created by labattula on 23/09/16.
 */

public class MyUtilApp extends Application {
    public static final String TAG = MyUtilApp.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static MyUtilApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //ButterKnife.setDebug(true);
        Iconify.with(new FontAwesomeModule()).with(new MaterialModule());
    }

    public static synchronized MyUtilApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

}
