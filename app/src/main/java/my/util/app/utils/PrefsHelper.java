package my.util.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefsHelper {
    private static PrefsHelper instance;

    private SharedPreferences sharedPrefs;

    public PrefsHelper(final Context context) {
        this.sharedPrefs = context.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void init(final Context context) {
        if (context != null) {
            instance = new PrefsHelper(context);
        }
    }

    public static PrefsHelper getInstance() {
        if (instance == null) {
            Log.d("DEBUG_LOG", "Prefs is null, call init.");
        }
        return instance;
    }

    public void setBooleanPref(final String key, final boolean value) {
        sharedPrefs.edit().putBoolean(key, value).apply();
    }

    public void setStringPref(final String key, final String value) {
        sharedPrefs.edit().putString(key, value).apply();
    }

    public void setIntPref(final String key, final int value) {
        sharedPrefs.edit().putInt(key, value).apply();
    }

    public boolean getBooleanPref(final String key, final boolean defaultValue) {
        return sharedPrefs.getBoolean(key, defaultValue);
    }

    public String getStringPref(final String key, final String defaultValue) {
        return sharedPrefs.getString(key, defaultValue);
    }

    public int getIntPref(final String key, final int defaultValue) {
        return sharedPrefs.getInt(key, defaultValue);
    }

    private void removePref(final String key) {
        sharedPrefs.edit().remove(key).apply();
    }
}
