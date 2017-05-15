package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

public class PayAccountDetailsFragment extends Fragment {

    @BindView(R.id.pay_input_account)
    EditText payAccountEdit;

    @BindView(R.id.progressBar)
    TextView progressBar;

    @BindView(R.id.pay_account_help)
    TextView payAccountHelp;

    String title;
    int page;


    public static PayAccountDetailsFragment newInstance(String title, int pageNumber) {
        PayAccountDetailsFragment fragment = new PayAccountDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PAGE_TITLE, title);
        bundle.putInt(Constants.PAGE_NUMBER, pageNumber);
        fragment.setArguments(bundle);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_pay_account_details, container, false);
        ButterKnife.bind(this, content);
        payAccountHelp.setVisibility(View.INVISIBLE);
        return content;
    }

    @OnClick(R.id.pay_info)
    protected void showPayInfo(View v) {
        payAccountHelp.setVisibility(View.VISIBLE);

        payAccountHelp.postDelayed(new Runnable() {
            @Override
            public void run() {
                payAccountHelp.setVisibility(View.INVISIBLE);
            }
        }, Constants.INFO_DISPLAY_TIME);
    }

    @OnClick(R.id.enter)
    protected void pay(View v) {
        String accountNo = payAccountEdit.getText().toString();
        if (!TextUtils.isEmpty(accountNo) && accountNo.length() >= Constants.ACC_NO_LEN) {
            progressBar.setVisibility(View.GONE);
            progressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    ((BaseActivity)getActivity()).updateFragment(Constants.FRAGMENTS.ACCOUNT);
                }
            }, Constants.PROGRESS_TIME);
        } else {
            Utils.showShortToast(getContext(), "Please enter a valid account no.");
        }
    }
}
