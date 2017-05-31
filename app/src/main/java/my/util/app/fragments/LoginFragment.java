package my.util.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.activity.AuthActivity;
import my.util.app.activity.SignUpActivity;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;
import sheet.bottom.com.networklib.models.global.MyLoaderResponse;
import sheet.bottom.com.networklib.models.tecoutil.DJavaClass;
import sheet.bottom.com.networklib.models.tecoutil.MyAuthResponse;
import sheet.bottom.com.networklib.serviceLayer.loaders.UserAuthLoader;

public class LoginFragment extends Fragment {

    @BindView(R.id.login_input_account)
    EditText accountEdit;
    @BindView(R.id.login_input_password)
    EditText passwordEdit;
    @BindView(R.id.progress)
    IconTextView progress;
    @BindView(R.id.btn_login)
    ImageView loginBtn;
    @BindView(R.id.btn_link_signup)
    Button signup;

    @BindView(R.id.login_account_help)
    TextView accountHelp;

    @BindView(R.id.login_progress)
    IconTextView progressView;

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
            String password = passwordEdit.getText().toString();
            if (!TextUtils.isEmpty(password) && password.length() >= Constants.ACC_NO_LEN) {
                if (getActivity() instanceof AuthActivity) {
                    loginBtn.setVisibility(View.GONE);
                    signup.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
//                    progress.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            ((AuthActivity) getActivity()).loginUser();
//                        }
//                    }, Constants.PROGRESS_TIME);
                    Bundle bundle = new Bundle();
                    bundle.putString("user", accountEdit.getText().toString());
                    bundle.putString("password", passwordEdit.getText().toString());
                    getLoaderManager().restartLoader(100, bundle, mLoginLoaderCallbacks);
                }
            } else {
                Utils.showShortToast(getContext(), "Please enter your password.");
            }
        } else {
            Utils.showShortToast(getContext(), "Please enter a valid account no.");
        }
    }

    @OnClick(R.id.btn_link_signup)
    protected void signUp(View v) {
        Intent signupIntent = new Intent();
        signupIntent.setClass(getActivity(), SignUpActivity.class);
        startActivity(signupIntent);
    }

    private LoaderManager.LoaderCallbacks<MyLoaderResponse<MyAuthResponse>> mLoginLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<MyLoaderResponse<MyAuthResponse>>() {

                @Override
                public Loader<MyLoaderResponse<MyAuthResponse>> onCreateLoader(int loaderId, Bundle bundle) {
                    String userName = bundle.getString("user");
                    String password = bundle.getString("password");
                    return new UserAuthLoader(getContext(), userName, password);
                }

                @Override
                public void onLoadFinished(Loader<MyLoaderResponse<MyAuthResponse>> loader, MyLoaderResponse<MyAuthResponse> loaderResult) {
                    progress.setVisibility(View.GONE);
                    ((AuthActivity) getActivity()).loginUser();
                }

                @Override
                public void onLoaderReset(Loader<MyLoaderResponse<MyAuthResponse>> loaderResult) {
                }
            };

}
