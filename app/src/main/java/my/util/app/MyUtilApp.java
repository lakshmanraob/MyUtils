package my.util.app;

import android.app.Application;
import android.util.Log;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

import java.util.ArrayList;

import my.util.app.models.IssueDetails;
import my.util.app.utils.Constants;
import my.util.app.utils.DbHelper;

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
        DataManager.getInstance(this).init();
    }

    public static synchronized MyUtilApp getInstance() {
        return mInstance;
    }
}
