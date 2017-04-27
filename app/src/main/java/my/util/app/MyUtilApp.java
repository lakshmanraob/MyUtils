package my.util.app;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

/*
 * Created by labattula on 23/09/16.
 */

public class MyUtilApp extends Application {
    public static final String TAG = MyUtilApp.class
            .getSimpleName();

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

}
