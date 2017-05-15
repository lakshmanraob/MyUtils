package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.utils.Constants;

public class SignupFragmentFour extends Fragment {

    private String title;
    private int page;

    public static SignupFragmentFour newInstance(String title, int pageNumber) {
        SignupFragmentFour fragment = new SignupFragmentFour();
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
        View signupView = inflater.inflate(R.layout.fragment_signup_four, container, false);
        ButterKnife.bind(this, signupView);
        return signupView;
    }
}
