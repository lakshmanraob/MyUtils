package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.utils.UtilsConstants;

public class PayAccountFragment extends Fragment {

    @BindView(R.id.pay_input_account)
    EditText payAccountEdit;

    @BindView(R.id.btn_pay)
    ImageView payBtn;

    @BindView(R.id.pay_account_help)
    TextView payAccountHelp;

    String title;
    int page;


    public static PayAccountFragment newInstance(String title, int pageNumber) {
        PayAccountFragment fragment = new PayAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UtilsConstants.PAGE_TITLE, title);
        bundle.putInt(UtilsConstants.PAGE_NUMBER, pageNumber);
        fragment.setArguments(bundle);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_pay_account, container, false);
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
        }, UtilsConstants.INFO_DISPLAY_TIME);
    }
}
