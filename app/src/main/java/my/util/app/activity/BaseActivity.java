package my.util.app.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.BindView;
import my.util.app.R;
import my.util.app.fragments.AccountDetailsFragment;
import my.util.app.fragments.BillDetailsFragment;
import my.util.app.fragments.ComplaintsFragment;
import my.util.app.fragments.ComplaintsListFragment;
import my.util.app.fragments.RecentBillsFragment;
import my.util.app.utils.Constants;

public class BaseActivity extends AppCompatActivity {

    private static int FRAGMENT_COUNT = 0;
    @BindView(R.id.tabs)
    TabLayout mBottomNavigationBar;

    public void setupBottomBar() {
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab().setText(getFragmentTitle(Constants.FRAGMENTS.BILLS)),true);
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab().setText(getFragmentTitle(Constants.FRAGMENTS.COMPLAINTS)));
        mBottomNavigationBar.addTab(mBottomNavigationBar.newTab().setText(getFragmentTitle(Constants.FRAGMENTS.ACCOUNT)));
        mBottomNavigationBar.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void addFragment(int fragment) {
        Log.d("DEBUG_LOG", "addFragment " + fragment);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, getFragmentContent(fragment), getFragmentTitle(fragment)).commit();
        FRAGMENT_COUNT++;
        updateBottomBar(fragment);
    }

    public void updateFragment(int fragment) {
        Log.d("DEBUG_LOG", "updateFragment " + fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, getFragmentContent(fragment), getFragmentTitle(fragment)).commit();
        updateBottomBar(fragment);
    }

    public void removeFragment(int fragment) {
        Log.d("DEBUG_LOG", "removeFragment " + fragment);
        getSupportFragmentManager().beginTransaction().remove(getFragmentContent(fragment)).commit();
        FRAGMENT_COUNT--;
        if (FRAGMENT_COUNT == 0) {
            Log.d("DEBUG_LOG", "FRAGMENT_COUNT == 0");
            addFragment(Constants.FRAGMENTS.COMPLAINTS);
        }
    }

    public void updateBottomBar(int fragment) {
        Log.d("DEBUG_LOG", "updateBottomBar " + fragment);
        /*if (mBottomNavigationBar != null) {
            if (fragment == Constants.FRAGMENTS.BILLS ||
                    fragment == Constants.FRAGMENTS.COMPLAINTS || fragment == Constants.FRAGMENTS.ACCOUNT) {
                mBottomNavigationBar.getMenu().getItem(fragment).setChecked(true);
            } else {
                mBottomNavigationBar.getMenu().getItem(Constants.FRAGMENTS.COMPLAINTS).setChecked(true);
            }
        }*/
    }

    public String getFragmentTitle(int position) {
        switch (position) {
            case Constants.FRAGMENTS.BILLS:
                return getString(R.string.recent_bills_title);
            case Constants.FRAGMENTS.COMPLAINTS:
            default:
                return getString(R.string.report_issue);
            case Constants.FRAGMENTS.ACCOUNT:
                return getString(R.string.account_details_title);
            case Constants.FRAGMENTS.NEW_COMPLAINT:
                return getString(R.string.new_complaint_title);
            case Constants.FRAGMENTS.BILL_DETAILS:
                return getString(R.string.bill_details_title);
        }
    }

    public Fragment getFragmentContent(int position) {
        switch (position) {
            case Constants.FRAGMENTS.BILLS:
                return RecentBillsFragment.newInstance(getFragmentTitle(position), null);
            case Constants.FRAGMENTS.COMPLAINTS:
            default:
                return ComplaintsListFragment.newInstance(getFragmentTitle(position), null);
            case Constants.FRAGMENTS.ACCOUNT:
                return AccountDetailsFragment.newInstance(getFragmentTitle(position), null);
            case Constants.FRAGMENTS.NEW_COMPLAINT:
                return ComplaintsFragment.newInstance(getFragmentTitle(position), null);
            case Constants.FRAGMENTS.BILL_DETAILS:
                return BillDetailsFragment.newInstance(getFragmentTitle(position), null);
        }
    }
}
