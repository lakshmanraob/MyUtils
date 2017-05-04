package my.util.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.adapter.ComplaintsListAdapter;
import my.util.app.utils.Constants;

public class ComplaintsListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ComplaintsListAdapter mComplaintsListAdapter;

    @BindView(R.id.complaints_list)
    protected RecyclerView mComplaintsListView;
    @BindView(R.id.search_open)
    protected IconTextView mSearchOpen;
    @BindView(R.id.search_close)
    protected IconTextView mSearchClose;
    @BindView(R.id.search_input_layout)
    protected FrameLayout mSearchInputLayout;
    @BindView(R.id.search_input)
    protected EditText mSearchInput;


    @OnClick(R.id.search_close)
    protected void closeSearchLayout(View v) {
        mSearchOpen.setVisibility(View.VISIBLE);
        mSearchInputLayout.setVisibility(View.GONE);
    }


    @OnClick(R.id.search_open)
    protected void openSearchLayout(View v) {
        mSearchOpen.setVisibility(View.GONE);
        mSearchInputLayout.setVisibility(View.VISIBLE);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_complaints_list, container, false);
        ButterKnife.bind(this, content);

        mComplaintsListAdapter = new ComplaintsListAdapter(getActivity(), Constants.getComplaintsList(getActivity()));
        LinearLayoutManager mngr = new LinearLayoutManager(getActivity());
        mComplaintsListView.setAdapter(mComplaintsListAdapter);
        mComplaintsListView.setLayoutManager(mngr);

        return content;
    }

}
