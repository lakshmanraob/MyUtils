package my.util.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import my.util.app.R;
import my.util.app.utils.StringUtils;

/**
 * Created by labattula on 27/04/17.
 */

public class UtilSplashActivity extends BaseActivity {

    private static final int MESSAGE_ID = 100;
    private static final long MESSAGE_DELAY = 5000L;

    private SplashHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {
            handler.removeMessages(MESSAGE_ID);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(MESSAGE_ID);
        }
    }

    private void startAuthActivity() {
        Intent intent = new Intent();
        intent.setClass(this, AuthActivity.class);
        startActivity(intent);
        handler = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message message = new Message();
        message.what = MESSAGE_ID;
        handler = new SplashHandler();
        handler.sendMessageDelayed(message, MESSAGE_DELAY);
    }

    private class SplashHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ID:
                    super.handleMessage(msg);
                    startAuthActivity();
                    finish();
                    break;
            }
        }
    }
}
