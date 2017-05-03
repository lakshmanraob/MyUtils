package my.util.app.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import my.util.app.R;
import my.util.app.adapter.ComplaintsListAdapter;
import my.util.app.adapter.ImagesAdapter;
import my.util.app.utils.Constants;
import my.util.app.utils.ImageCaptureListener;
import my.util.app.utils.PhotoDetails;
import my.util.app.utils.Utils;

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
