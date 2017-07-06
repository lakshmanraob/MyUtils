package my.util.app.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.AuthActivity;
import my.util.app.utils.Constants;
import my.util.app.utils.PrefsHelper;
import my.util.app.utils.Utils;

public class LoginPinFragment extends Fragment {

    @BindView(R.id.login_input_pin)
    EditText pinEntered;
    @BindView(R.id.progress)
    IconTextView progress;
    @BindView(R.id.btn_login)
    ImageView loginBtn;
    @BindView(R.id.btn_link_signup)
    Button signup;
    @BindView(R.id.btn_link_reset)
    Button resetPin;
    @BindView(R.id.login_progress)
    IconTextView progressView;

    private String title;
    private int page;

    public static LoginPinFragment newInstance(String title, int pageNumber) {
        LoginPinFragment fragment = new LoginPinFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PAGE_TITLE, title);
        args.putInt(Constants.PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(Constants.PAGE_TITLE);
        page = getArguments().getInt(Constants.PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login_pin, container, false);
        ButterKnife.bind(this, loginView);
        return loginView;
    }

    @OnClick(R.id.btn_login)
    protected void login(View v) {
        progressView.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);
        String pin = pinEntered.getText().toString();
        new CheckUserPin(pin).execute();
    }

    @OnClick(R.id.btn_link_reset)
    protected void resetPin(View v) {
        Utils.showProgressBarWithListener(getActivity(), new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                PrefsHelper.getInstance().setStringPref(Constants.USER_PIN, "");
                Activity currentActivity = getActivity();
                startActivity(new Intent(currentActivity, AuthActivity.class));
                currentActivity.finish();
            }
        });
    }

    private class CheckUserPin extends AsyncTask<Void, Void, Void> {
        String userPin;

        public CheckUserPin(String pin) {
            this.userPin = pin;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(Constants.PROCESS_TIME);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressView.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(userPin) && userPin.length() >= Constants.PIN_LEN) {
                String savedPin = DataManager.getInstance(getContext()).getUserPin();
                if (!TextUtils.isEmpty(savedPin)) {
                    if (userPin.equals(savedPin)) {
                        if (getActivity() instanceof AuthActivity) {
                            ((AuthActivity) getActivity()).loginUser();
                        }
                    } else {
                        Utils.showShortToast(getContext(), "Please enter your saved PIN.");
                    }
                } else {
                    Utils.showShortToast(getContext(), "Please enter your saved PIN.");
                }
            } else {
                Utils.showShortToast(getContext(), "Please enter your saved PIN.");
            }
        }
    }
}
