package my.util.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.activity.AuthActivity;
import my.util.app.activity.MainActivity;
import my.util.app.activity.SignUpActivity;
import my.util.app.utils.UtilsConstants;

public class LoginFragment extends Fragment {

    @BindView(R.id.login_input_account)
    EditText accountEdit;
    @BindView(R.id.login_input_password)
    EditText passwordEdit;

    @BindView(R.id.login_account_help)
    TextView accountHelp;

    @BindView(R.id.login_progress)
    IconTextView progressView;

    private String title;
    private int page;

    public static LoginFragment newInstance(String title, int pageNumber) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(UtilsConstants.PAGE_TITLE, title);
        args.putInt(UtilsConstants.PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(UtilsConstants.PAGE_TITLE);
        page = getArguments().getInt(UtilsConstants.PAGE_NUMBER);
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
        progressView.setVisibility(View.VISIBLE);

        accountHelp.postDelayed(new Runnable() {
            @Override
            public void run() {
                accountHelp.setVisibility(View.INVISIBLE);
                progressView.setVisibility(View.INVISIBLE);
            }
        }, UtilsConstants.INFO_DISPLAY_TIME);
    }

    @OnClick(R.id.btn_login)
    protected void login(View v) {
        if(getActivity() instanceof AuthActivity) {
            ((AuthActivity)getActivity()).loginUser();
        }
    }

    @OnClick(R.id.btn_link_signup)
    protected void signUp(View v) {
        Intent signupIntent = new Intent();
        signupIntent.setClass(getActivity(), SignUpActivity.class);
        startActivity(signupIntent);
    }

}
