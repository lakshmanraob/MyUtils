package my.util.app.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.AuthActivity;
import my.util.app.activity.SignUpActivity;
import my.util.app.models.LoginResponse;
import my.util.app.network.global.MyLoaderResponse;
import my.util.app.network.loaders.UserAuthLoader;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;
import okhttp3.Headers;

public class LoginFragment extends Fragment {

    @BindView(R.id.login_input_account)
    EditText accountEdit;
    @BindView(R.id.login_input_ssn)
    EditText ssnEdit;
    @BindView(R.id.pin)
    EditText pinEntered;
    @BindView(R.id.pin_confirm)
    EditText pinConfirmed;
    @BindView(R.id.progress)
    IconTextView progress;
    @BindView(R.id.pin_progress)
    IconTextView pinProgress;
    @BindView(R.id.btn_login)
    ImageView loginBtn;
    @BindView(R.id.btn_set_pin)
    ImageView pinSetBtn;
    @BindView(R.id.btn_link_signup)
    Button signup;

    @BindView(R.id.login_account_help)
    TextView accountHelp;

    @BindView(R.id.login_progress)
    IconTextView progressView;

    @BindView(R.id.login_frame)
    LinearLayout loginFrame;
    @BindView(R.id.set_pin_frame)
    LinearLayout setPinFrame;

    private String title;
    private int page;

    public static LoginFragment newInstance(String title, int pageNumber) {
        LoginFragment fragment = new LoginFragment();
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
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, loginView);
        accountHelp.setVisibility(View.INVISIBLE);
        return loginView;
    }

    @OnClick(R.id.login_info)
    protected void loginInfo(View v) {
        accountHelp.setVisibility(View.VISIBLE);
        //progressView.setVisibility(View.VISIBLE);

        accountHelp.postDelayed(new Runnable() {
            @Override
            public void run() {
                accountHelp.setVisibility(View.INVISIBLE);
                progressView.setVisibility(View.INVISIBLE);
            }
        }, Constants.INFO_DISPLAY_TIME);
    }

    @OnClick(R.id.btn_login)
    protected void login(View v) {
        String accountNo = accountEdit.getText().toString();
        if (!TextUtils.isEmpty(accountNo) && accountNo.length() >= Constants.ACC_NO_LEN) {
            String ssn = ssnEdit.getText().toString();
            if (!TextUtils.isEmpty(ssn) && ssn.length() == Constants.SSN_LEN) {
                if (getActivity() instanceof AuthActivity) {
                    DataManager.getInstance(getContext()).setAccNo(accountNo);
                    DataManager.getInstance(getContext()).setSsn(ssn);
                    loginBtn.setVisibility(View.GONE);
                    signup.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    callLoginApi();
                }
            } else {
                Utils.showShortToast(getContext(), "Please enter the last 4 digits of your SSN.");
            }
        } else {
            Utils.showShortToast(getContext(), "Please enter a valid account no.");
        }
    }

    @OnClick(R.id.btn_set_pin)
    protected void setPin(View v) {
        String pinEnteredDigits = pinEntered.getText().toString();
        String pinConfirmedDigits = pinConfirmed.getText().toString();
        if (TextUtils.isEmpty(pinEnteredDigits)) {
            Utils.showShortToast(getContext(), "Please enter a desired PIN.");
        } else if (pinEnteredDigits.length() != Constants.PIN_LEN) {
            Utils.showShortToast(getContext(), "PIN should be 4 digits long.");
        } else if (TextUtils.isEmpty(pinConfirmedDigits) || pinConfirmedDigits.length() != Constants.PIN_LEN) {
            Utils.showShortToast(getContext(), "Please confirm the entered PIN.");
        } else if (!pinEnteredDigits.equals(pinConfirmedDigits)) {
            Utils.showShortToast(getContext(), "Entered and confirmed PIN do not match.");
        } else if (!TextUtils.isEmpty(pinEnteredDigits) &&
                !TextUtils.isEmpty(pinConfirmedDigits) &&
                pinEnteredDigits.length() == Constants.PIN_LEN &&
                pinConfirmedDigits.length() == Constants.PIN_LEN &&
                pinEnteredDigits.equals(pinConfirmedDigits)) {
            DataManager.getInstance(getContext()).setUserPin(pinEnteredDigits);
            pinSetBtn.setVisibility(View.GONE);
            pinProgress.setVisibility(View.VISIBLE);
            new SetUserPin().execute();
        } else {
            Utils.showShortToast(getContext(), "Please enter and confirm a 4 digit PIN.");
        }
    }

    @OnClick(R.id.btn_link_signup)
    protected void signUp(View v) {
        Intent signupIntent = new Intent();
        signupIntent.setClass(getActivity(), SignUpActivity.class);
        startActivity(signupIntent);
    }

    private String trimPath(String raw) {
        String modified = null;
        if (raw.contains("path")) {
            String[] split = raw.split("path");
            modified = split[0];
        } else {
            modified = raw;
        }
        return modified;
    }

    private void callLoginApi() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_LABEL, Constants.USERNAME);
        bundle.putString(Constants.SSN_LABEL, Constants.PASSWORD);
        getLoaderManager().restartLoader(100, bundle, mLoginLoaderCallbacks);
    }

    private void loginToApp(MyLoaderResponse<LoginResponse> loaderResult) {
        if (loaderResult != null && loaderResult.getHeaders() != null) {
            Headers headerList = loaderResult.getHeaders();
            DataManager.getInstance(getContext()).setUserCsrfToken(headerList.get(Constants.CSRF_TOKEN));
            List<String> cookies = headerList.values(Constants.USER_COOKIE);
            for (int i = 0; i < cookies.size(); i++) {
                String cookie = cookies.get(i);
                Log.d("DEBUG_LOG", "i " + i + " :  " + cookie);
                if (i == 0) {
                    DataManager.getInstance(getContext()).setUserCookie1(trimPath(cookie));
                } else if (i == 1) {
                    DataManager.getInstance(getContext()).setUserCookie2(trimPath(cookie));
                }
            }
            /*Log.d("DEBUG_LOG", "set all complaints");
            DataManager.getInstance(getContext()).setAllComplaints(loaderResult.getData());*/
            Log.d("DEBUG_LOG", "DONE " + loaderResult.getData());
            loginFrame.setVisibility(View.GONE);
            setPinFrame.setVisibility(View.VISIBLE);
        } else {
            loginFrame.setVisibility(View.VISIBLE);
            setPinFrame.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            Log.d("DEBUG_LOG", "set error");
            Utils.showSubmitDialog(getContext(), R.layout.login_fail_dialog);
        }
    }

    private LoaderManager.LoaderCallbacks<MyLoaderResponse<LoginResponse>> mLoginLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<MyLoaderResponse<LoginResponse>>() {

                @Override
                public Loader<MyLoaderResponse<LoginResponse>> onCreateLoader(int loaderId, Bundle bundle) {
                    String userName = bundle.getString(Constants.USER_LABEL);
                    String password = bundle.getString(Constants.SSN_LABEL);
                    return new UserAuthLoader(getContext(), userName, password);
                }

                @Override
                public void onLoadFinished(Loader<MyLoaderResponse<LoginResponse>> loader, MyLoaderResponse<LoginResponse> loaderResult) {
                    progress.setVisibility(View.GONE);
                    loginToApp(loaderResult);
                }

                @Override
                public void onLoaderReset(Loader<MyLoaderResponse<LoginResponse>> loaderResult) {
                }
            };


    private class SetUserPin extends AsyncTask<Void, Void, Void> {
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
            pinProgress.setVisibility(View.GONE);
            ((AuthActivity) getActivity()).loginUser();
        }
    }

}
