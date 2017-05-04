package my.util.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.fragments.AccountDetailsFragment;
import my.util.app.fragments.ComplaintsFragment;
import my.util.app.fragments.ComplaintsListFragment;
import my.util.app.fragments.PlaceholderFragment;
import my.util.app.fragments.RecentBillsFragment;
import my.util.app.utils.Constants;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.bottom_tab)
    BottomNavigationView mBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBottomNavigationBar.getMenu().getItem(1).setChecked(true);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, getFragmentContent(1), getFragmentTitle(1)).commit();

        mBottomNavigationBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.recent_bills:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, getFragmentContent(0), getFragmentTitle(0)).commit();
                                break;
                            case R.id.complaints:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, getFragmentContent(1), getFragmentTitle(1)).commit();
                                break;
                            case R.id.account_details:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, getFragmentContent(2), getFragmentTitle(2)).commit();
                                break;
                        }
                        return true;
                    }
                });
    }

    private String getFragmentTitle(int position) {
        switch (position) {
            case 0:
                return getString(R.string.recent_bills_title);
            case 1:
                return getString(R.string.report_issue);
            case 2:
                return getString(R.string.account_details_title);
            default:
                return getString(R.string.recent_bills_title);
        }
    }

    private Fragment getFragmentContent(int position) {
        switch (position) {
            case 0:
                return RecentBillsFragment.newInstance(getFragmentTitle(position), null);
            case 1:
                return ComplaintsListFragment.newInstance(getFragmentTitle(position), null);
            case 2:
                return AccountDetailsFragment.newInstance(getFragmentTitle(position), null);
        }
        return null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            List<Fragment> allFragments = getSupportFragmentManager().getFragments();
            if (allFragments != null && !allFragments.isEmpty()) {
                for (Fragment fragment : allFragments) {
                    if (fragment instanceof ComplaintsFragment) {
                        Drawable image = new BitmapDrawable(getResources(), photo);
                        ((ComplaintsFragment) fragment).updateImages(image);
                    }
                }
            }
        }
    }
}
