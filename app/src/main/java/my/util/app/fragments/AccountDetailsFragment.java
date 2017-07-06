package my.util.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.DetailsView;
import my.util.app.R;
import my.util.app.adapter.UserBilldetailsAdapter;
import my.util.app.models.AccountDetailsResponse;
import my.util.app.models.AccountResult;
import my.util.app.models.MyAuthResponse;
import my.util.app.models.UserDetails;
import my.util.app.network.global.MyLoaderResponse;
import my.util.app.network.loaders.AccountDetailsLoader;
import my.util.app.network.loaders.FetchComplaintsLoader;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;

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
    LinearLayout footerView;
    private static int prev = -1;

    AccountDetailsResponse accountDetailsData;

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
        Log.d("DEBUG_LOG", "Acc details frag");

        Bundle bundle = new Bundle();
        bundle.putString(Constants.USER_LABEL, Constants.USERNAME);
        bundle.putString(Constants.SSN_LABEL, Constants.PASSWORD);
        getLoaderManager().restartLoader(100, bundle, mAccountDetailsLoaderCallbacks);
        return content;
    }

    private void createHeaderView() {
        LayoutInflater inflatter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = (LinearLayout) inflatter.inflate(R.layout.ud_header, null);

        AccountResult userDetails = accountDetailsData.getD().getResults()[0];
        mAccountNumber = (TextView) headerView.findViewById(R.id.ud_accountnumber);
        mAccountNumber.setText(userDetails.getBpart());

        mFirstName = (DetailsView) headerView.findViewById(R.id.ud_firstName);
        mFirstName.setContent(userDetails.getFirstName());

        mLastName = (DetailsView) headerView.findViewById(R.id.ud_lastName);
        mLastName.setContent(userDetails.getLastName());

        mDob = (DetailsView) headerView.findViewById(R.id.ud_dob);
        mDob.setContent(Utils.convertDate(userDetails.getDob()));

        mPhoneNumber = (DetailsView) headerView.findViewById(R.id.ud_phone);
        mPhoneNumber.setContent(userDetails.getPhone());

        mEmail = (DetailsView) headerView.findViewById(R.id.ud_email);
        mEmail.setContent(userDetails.getEmail());

        mAddress = (DetailsView) headerView.findViewById(R.id.ud_address);
        mAddress.setContent(userDetails.getAddress());
    }

    private void createFooterView() {
        LayoutInflater inflatter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = (LinearLayout) inflatter.inflate(R.layout.ud_footer, null);

        ((Button) footerView.findViewById(R.id.logout_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.logoutUser(getActivity());
            }
        });
    }

    private void updateView() {

        createHeaderView();
        mAccountList.addHeaderView(headerView);

        createFooterView();
        mAccountList.addFooterView(footerView);

        //Setting up the adapter for the spinner
        UserBilldetailsAdapter adapter = new UserBilldetailsAdapter(getContext(), Constants.getBillTitles(), Constants.getRecentBills(getContext()));
        mAccountList.setAdapter(adapter);
        mAccountList.setGroupIndicator(null);
        mAccountList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prev != -1 && prev != groupPosition) {
                    mAccountList.collapseGroup(prev);
                }
                prev = groupPosition;
            }
        });

    }

    private LoaderManager.LoaderCallbacks<MyLoaderResponse<AccountDetailsResponse>> mAccountDetailsLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<MyLoaderResponse<AccountDetailsResponse>>() {

                @Override
                public Loader<MyLoaderResponse<AccountDetailsResponse>> onCreateLoader(int loaderId, Bundle bundle) {
                    Log.d("DEBUG_LOG", "onCreateLoader Acc Details");
                    Utils.showProgressDialog(getContext());
                    String userName = bundle.getString(Constants.USER_LABEL);
                    String password = bundle.getString(Constants.SSN_LABEL);
                    return new AccountDetailsLoader(getContext(), userName, password);
                }

                @Override
                public void onLoadFinished(Loader<MyLoaderResponse<AccountDetailsResponse>> loader, MyLoaderResponse<AccountDetailsResponse> loaderResult) {
                    if (loaderResult != null && loaderResult.getData() != null) {
                        Log.d("DEBUG_LOG", "fetch acc details onLoadFinished");
                        Utils.hideProgressDialog();
                        accountDetailsData = loaderResult.getData();
                        if (accountDetailsData != null && accountDetailsData.getD() != null &&
                                accountDetailsData.getD().getResults() != null  && accountDetailsData.getD().getResults().length > 0) {
                            Log.d("DEBUG_LOG", "refresh account details");
                            updateView();
                        } else {
                            Log.d("DEBUG_LOG", "refresh  account details FAIL");
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<MyLoaderResponse<AccountDetailsResponse>> loaderResult) {
                }
            };


}
