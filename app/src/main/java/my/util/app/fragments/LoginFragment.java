package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.utils.UtilsConstants;

/**
 * Created by labattula on 27/04/17.
 */

public class LoginFragment extends Fragment {

    @BindView(R.id.login_input_account)
    EditText accountEdit;
    @BindView(R.id.login_input_password)
    EditText passwordEdit;

    @BindView(R.id.login_account_help)
    TextView accountHelp;

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
        return loginView;
    }

}
