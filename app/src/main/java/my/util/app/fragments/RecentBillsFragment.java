package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.adapter.AddressExpandableListAdapter;
import my.util.app.models.BillDetails;
import my.util.app.utils.Constants;

public class RecentBillsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int prev = -1;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.address_expandable_list)
    protected ExpandableListView addressExpandableList;

    public RecentBillsFragment() {
    }

    public static RecentBillsFragment newInstance(String param1, String param2) {
        RecentBillsFragment fragment = new RecentBillsFragment();
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
        View content = inflater.inflate(R.layout.fragment_recent_bills, container, false);
        ButterKnife.bind(this, content);
        HashMap<String, List<BillDetails>> allBills = Constants.getRecentBills(getContext());
        addressExpandableList.setAdapter(new AddressExpandableListAdapter(getActivity(), Constants.getBillTitles(allBills), allBills));
        addressExpandableList.setGroupIndicator(null);
        addressExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prev != -1 && prev != groupPosition) {
                    addressExpandableList.collapseGroup(prev);
                }
                prev = groupPosition;
            }
        });
        addressExpandableList.expandGroup(0);
        return content;
    }

}
