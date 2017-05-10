package my.util.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.DetailsView;
import my.util.app.R;
import my.util.app.adapter.UserBilldetailsAdapter;
import my.util.app.models.UserDetails;
import my.util.app.utils.Constants;

public class AccountDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView mAccountNumber;

    DetailsView mFirstName;
    DetailsView mLastName;
    DetailsView mDob;
    DetailsView mPhoneNumber;
    DetailsView mEmail;
    DetailsView mAddress;

    @BindView(R.id.ud_account_list)
    ExpandableListView mAccountList;

    LinearLayout headerView;

    public AccountDetailsFragment() {
    }

    public static AccountDetailsFragment newInstance(String param1, String param2) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_account_details, container, false);
        ButterKnife.bind(this, content);
        updateView();
        return content;
    }

    private void createHeaderView() {
        LayoutInflater inflatter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = (LinearLayout) inflatter.inflate(R.layout.ud_header, null);

        UserDetails userDetails = getUserDetails();
        mAccountNumber = (TextView) headerView.findViewById(R.id.ud_accountnumber);
        mAccountNumber.setText(userDetails.getAccountNumber());

        mFirstName = (DetailsView) headerView.findViewById(R.id.ud_firstName);
        mFirstName.setContent(userDetails.getFirstName());

        mLastName = (DetailsView) headerView.findViewById(R.id.ud_lastName);
        mFirstName.setContent(userDetails.getFirstName());

        mDob = (DetailsView) headerView.findViewById(R.id.ud_dob);
        mDob.setContent(userDetails.getDob());

        mPhoneNumber = (DetailsView) headerView.findViewById(R.id.ud_phone);
        mPhoneNumber.setContent(userDetails.getPhoneNumber());

        mEmail = (DetailsView) headerView.findViewById(R.id.ud_email);
        mEmail.setContent(userDetails.getEmail());

        mAddress = (DetailsView) headerView.findViewById(R.id.ud_address);
        mAddress.setContent(userDetails.getAddress());
    }

    private void updateView() {

        createHeaderView();

        mAccountList.addHeaderView(headerView);

        //Setting up the adapter for the spinner
        UserBilldetailsAdapter adapter = new UserBilldetailsAdapter(getContext(), Constants.getBillTitles(), Constants.getRecentBills());
        mAccountList.setAdapter(adapter);

    }

    private UserDetails getUserDetails() {

        UserDetails userDetails =
                new UserDetails("9876543212", "First", "Last", "10/3/1997", "123456789",
                        "labattula@deloitte.com", "Marathahalli, Bangalore", Constants.getRecentBills());

        return userDetails;

    }

}
