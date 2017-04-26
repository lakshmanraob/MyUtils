package my.util.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import my.util.app.R;
import my.util.app.adapter.BillItemsAdapter;

import sheet.bottom.com.networklib.models.global.StLoaderResponse;
import sheet.bottom.com.networklib.models.stackexchange.StackItem;
import sheet.bottom.com.networklib.models.stackexchange.StackResponse;
import sheet.bottom.com.networklib.serviceLayer.StackItemsLoader;

/**
 * Created by labattula on 15/09/16.
 */
public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_QUERY = "ARG_QUERY";
    private int mPageNo;
    private String query;

    private RecyclerView stackRecycler = null;
    private ProgressBar mProgressBar = null;
    private TextView mTextView = null;

    private final String TAG = PageFragment.class.getSimpleName();

    private BillItemsAdapter mAdapter;

    List<StackItem> stackItemList;

    enum ListStatus {
        LIST, ERROR, PROGRESS
    }

    public static PageFragment newInstance(String query) {

        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
        query = getArguments().getString(ARG_QUERY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        mTextView = (TextView) view.findViewById(R.id.fragment_txt);
        mTextView.setText("Fragment # " + mPageNo +" loading...");
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        stackRecycler = (RecyclerView) view.findViewById(R.id.stackItemsListView);

        startLoader(query);
        return view;
    }

    private void showStatus(ListStatus listStatus) {
        resetViewsToGone();
        switch (listStatus) {
            case LIST:
                stackRecycler.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                mTextView.setVisibility(View.VISIBLE);
                break;
            case PROGRESS:
                mTextView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetViewsToGone() {
        stackRecycler.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void setListView() {
        mAdapter = new BillItemsAdapter(getActivity());
        mAdapter.setStackItemList(stackItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        stackRecycler.setLayoutManager(mLayoutManager);
        stackRecycler.setItemAnimator(new DefaultItemAnimator());
        stackRecycler.setAdapter(mAdapter);
    }

    private void startLoader(String query) {
        showStatus(ListStatus.PROGRESS);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_QUERY, query);
        getLoaderManager().restartLoader(100, bundle, retrieveStackItemsCallback);
    }

    private List<StackItem> getListItems(StackItem[] items) {
        List<StackItem> stackList = new ArrayList<>();

        for (StackItem it : items) {
            stackList.add(it);
        }

        return stackList;
    }

    /**
     * Loader for getting the StackOverFlow posts
     */
    private LoaderManager.LoaderCallbacks<StLoaderResponse<StackResponse>> retrieveStackItemsCallback =
            new LoaderManager.LoaderCallbacks<StLoaderResponse<StackResponse>>() {

                @Override
                public Loader<StLoaderResponse<StackResponse>> onCreateLoader(int loaderId, Bundle bundle) {
                    return new StackItemsLoader(getActivity(), bundle.getString(ARG_QUERY));
                }

                @Override
                public void onLoadFinished(Loader<StLoaderResponse<StackResponse>> loader, StLoaderResponse<StackResponse> loaderResult) {
                    Log.i(TAG, "onLoadFinished: " + loaderResult.getData());
                    if (loaderResult.getData() != null) {
                        showStatus(ListStatus.LIST);
                        stackItemList = getListItems(loaderResult.getData().getItems());
                        setListView();
                        Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                    } else {
                        showStatus(ListStatus.ERROR);
                        Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onLoaderReset(Loader<StLoaderResponse<StackResponse>> loaderResult) {
                }
            };
}
