package my.util.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.DetailsView;
import my.util.app.R;
import my.util.app.adapter.UserBilldetailsAdapter;
import my.util.app.models.ServiceAddress;
import my.util.app.models.UserDetails;
import my.util.app.utils.Constants;
import my.util.app.utils.Utils;
import my.util.app.utils.UtilsConstants;

/**
 * Created by labattula on 03/05/17.
 */

public class AccountDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.ud_accountnumber)
    TextView mAccountNumber;

    @BindView(R.id.ud_firstName)
    DetailsView mFirstName;

    @BindView(R.id.ud_lastName)
    DetailsView mLastName;

    @BindView(R.id.ud_dob)
    DetailsView mDob;

    @BindView(R.id.ud_phone)
    DetailsView mPhoneNumber;

    @BindView(R.id.ud_email)
    DetailsView mEmail;

    @BindView(R.id.ud_address)
    DetailsView mAddress;

    @BindView(R.id.ud_account_list)
    ExpandableListView mAccountList;

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

    private void updateView() {
        UserDetails userDetails = getUserDetails();
        mAccountNumber.setText(userDetails.getAccountNumber());

        mFirstName.setContent(userDetails.getFirstName());

        mLastName.setContent(userDetails.getLastName());

        mPhoneNumber.setContent(userDetails.getPhoneNumber());

        mEmail.setContent(userDetails.getEmail());

        mDob.setContent(userDetails.getDob());

        mAddress.setContent(userDetails.getAddress());

        //Setting up the adapter for the spinner
        UserBilldetailsAdapter adapter = new UserBilldetailsAdapter(getContext(), Constants.getBillTitles(), Constants.getRecentBills());
        mAccountList.setAdapter(adapter);
    }

    private UserDetails getUserDetails() {
        ServiceAddress address1 =
                new ServiceAddress("Home", "Home", "streetAddress2", "Bangalore");

        ServiceAddress address2 =
                new ServiceAddress("Office", "OfficeAddress", "streetAddress2", "Mangalore");

//        ArrayList<ServiceAddress> addresses = new ArrayList<>();
//        addresses.add(address1);
//        addresses.add(address2);

        UserDetails userDetails =
                new UserDetails("9876543212", "First", "Last", "10/3/1997", "123456789",
                        "labattula@deloitte.com", "someAddress", Constants.getRecentBills());

        return userDetails;

    }


//    public class MySpinnerAdapter extends BaseAdapter {
//
//        Context context;
//        LayoutInflater inflter;
//        ArrayList<ServiceAddress> baseAddresses;
//
//        public MySpinnerAdapter(Context context, ArrayList<ServiceAddress> addresses) {
//            this.context = context;
//            this.baseAddresses = addresses;
//            inflter = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            return baseAddresses.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            View view = inflter.inflate(R.layout.view_details_item, null);
//
//            TextView mheadingText = (TextView) view.findViewById(R.id.details_heading);
//            TextView mcontentText = (TextView) view.findViewById(R.id.deatils_content);
//
//            ServiceAddress serviceAddress = baseAddresses.get(position);
//
//            mheadingText.setText(serviceAddress.getAddressName());
//            mcontentText.setText(serviceAddress.getStreetAddress1());
//
//            return view;
//        }
//    }

}
