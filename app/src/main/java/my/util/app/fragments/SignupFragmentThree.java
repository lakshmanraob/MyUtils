package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.utils.UtilsConstants;

public class SignupFragmentThree extends Fragment {

    private String title;
    private int page;

    public static SignupFragmentThree newInstance(String title, int pageNumber) {
        SignupFragmentThree fragment = new SignupFragmentThree();
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
        View signupView = inflater.inflate(R.layout.fragment_signup_three, container, false);
        ButterKnife.bind(this, signupView);
        return signupView;
    }
}
