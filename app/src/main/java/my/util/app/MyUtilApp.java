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
    private static DbHelper mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //ButterKnife.setDebug(true);
        Iconify.with(new FontAwesomeModule()).with(new MaterialModule());
        mDbHelper = new DbHelper(this);
        checkAndEnterDummyData();
    }

    public static synchronized MyUtilApp getInstance() {
        return mInstance;
    }

    public static synchronized DbHelper getDbHelper() {
        return mDbHelper;
    }

    private void checkAndEnterDummyData(){
        Log.d("DEBUG_LOG", "checkAndEnterDummyData");
        ArrayList<IssueDetails> allComplaints = mDbHelper.getAllComplaints();
        if (allComplaints == null || allComplaints.size() <= 0) {
            Log.d("DEBUG_LOG", "no data exists");
            ArrayList<IssueDetails> dummyList = Constants.getDummyComplaintsList(this);
            for(IssueDetails issue: dummyList){
                mDbHelper.addNewComplaint(issue);
            }
        }
    }

}
