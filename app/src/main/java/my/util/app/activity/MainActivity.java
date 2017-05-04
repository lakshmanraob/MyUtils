package my.util.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import my.util.app.R;
import my.util.app.fragments.ComplaintsFragment;
import my.util.app.fragments.ComplaintsListFragment;
import my.util.app.fragments.PlaceholderFragment;
import my.util.app.fragments.RecentBillsFragment;
import my.util.app.utils.Constants;

public class MainActivity extends BaseActivity {

    TabLayout mTabLayout;

    private static final int PAGE_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);

        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());

        if (mViewPager != null) {
            mViewPager.setAdapter(pageAdapter);
        }

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pageAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

    }

    private String getTitle(int position) {
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

    /**
     * Adapter to handle the Fragment display
     */
    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }


        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab_bottom, null);
            TextView title = (TextView) view.findViewById(R.id.custom_title);
            title.setText(getTitle(position).toUpperCase());
            ImageView icon = (ImageView) view.findViewById(R.id.custom_icon);
            icon.setVisibility(View.GONE);
            return view;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RecentBillsFragment.newInstance(getTitle(position), null);
                case 1:
                    return ComplaintsListFragment.newInstance(getTitle(position), null);
                case 2:
                    return PlaceholderFragment.newInstance(getTitle(position), null);
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getTitle(position);
        }
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
