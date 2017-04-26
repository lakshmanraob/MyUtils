package sheet.bottom.com.bottomsheetapp.fingerprint;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import sheet.bottom.com.bottomsheetapp.MainActivity;

/**
 * Created by labattula on 08/12/16.
 */

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private Context mContext;
    private CancellationSignal mCancellationSignal;
    private OnCloseActivity onCloseActivity;

    public FingerPrintHandler(Context context) {
        this.mContext = context;
    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject,
                          OnCloseActivity closeActivity) {
        mCancellationSignal = new CancellationSignal();
        this.onCloseActivity = closeActivity;
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        manager.authenticate(cryptoObject, mCancellationSignal, 0, this, null);


    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        showToast("onAuthenticationError " + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        showToast("onAuthenticationHelp " + helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        showToast("onAuthenticationSucceeded ");
        launchMainActivity();

    }

    private void launchMainActivity() {
        onCloseActivity.closeCurrentActivity();
        Intent launchIntent = new Intent();
        launchIntent.setClass(mContext, MainActivity.class);
        mContext.startActivity(launchIntent);
    }

    @Override
    public void onAuthenticationFailed() {
        showToast("onAuthenticationFailed ");
    }

    private void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }
}
