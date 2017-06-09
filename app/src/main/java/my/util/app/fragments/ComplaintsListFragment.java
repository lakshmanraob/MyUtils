package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.activity.BaseActivity;
import my.util.app.adapter.ComplaintsListAdapter;
import my.util.app.models.LoginResult;
import my.util.app.models.MyAuthResponse;
import my.util.app.utils.Constants;

public class ComplaintsListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ComplaintsListAdapter mComplaintsListAdapter;
    private MyAuthResponse allComplaintsData;
    private LoginResult[] complaintsList;

    @BindView(R.id.complaints_list)
    protected RecyclerView mComplaintsListView;
    @BindView(R.id.search_open)
    protected IconTextView mSearchOpen;
    @BindView(R.id.search_close)
    protected IconTextView mSearchClose;
    @BindView(R.id.search_input_layout)
    protected FrameLayout mSearchInputLayout;
    @BindView(R.id.search_divider)
    protected View mSearchDivider;
    @BindView(R.id.search_input)
    protected EditText mSearchInput;

    @OnClick(R.id.report_btn)
    protected void reportNewIssue(View v) {
        ((BaseActivity) getActivity()).updateFragment(Constants.FRAGMENTS.NEW_COMPLAINT);
    }

    @OnClick(R.id.open_map_view)
    protected void openMapView(View v) {
        ((BaseActivity) getActivity()).updateFragment(Constants.FRAGMENTS.ISSUES_MAP_VIEW);
    }

    @OnClick(R.id.search_close)
    protected void closeSearchLayout(View v) {
        mSearchInput.setText("");
        mSearchInputLayout.setVisibility(View.GONE);
        mSearchOpen.setVisibility(View.VISIBLE);
        mSearchDivider.setVisibility(View.VISIBLE);
        refreshList();
    }

    @OnClick(R.id.search_open)
    protected void openSearchLayout(View v) {
        mSearchOpen.setVisibility(View.GONE);
        mSearchInputLayout.setVisibility(View.VISIBLE);
        mSearchDivider.setVisibility(View.GONE);
    }

    public ComplaintsListFragment() {
    }

    public static ComplaintsListFragment newInstance(String param1, String param2) {
        ComplaintsListFragment fragment = new ComplaintsListFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList(){
        mComplaintsListAdapter.updateList(DataManager.getInstance(getActivity()).getComplaintsList());
        mComplaintsListView.invalidate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_complaints_list, container, false);
        ButterKnife.bind(this, content);
        allComplaintsData = DataManager.getInstance(getActivity()).getAllComplaints();
        complaintsList = allComplaintsData.getD().getResults();

        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && android.text.TextUtils.isDigitsOnly(s.toString()) && complaintsList != null && complaintsList.length > 0) {
                    //complaintsList = Utils.filterComplaintsList(complaintsList, s.toString()); // TODO:
                    mComplaintsListAdapter.updateList(complaintsList);// TODO:
                    mComplaintsListView.invalidate();
                } else {
                    refreshList();
                }
            }
        });

        mComplaintsListAdapter = new ComplaintsListAdapter(complaintsList);
        LinearLayoutManager mngr = new LinearLayoutManager(getActivity());
        mComplaintsListView.setAdapter(mComplaintsListAdapter);
        mComplaintsListView.setLayoutManager(mngr);
        mComplaintsListView.invalidate();

        return content;
    }

}
