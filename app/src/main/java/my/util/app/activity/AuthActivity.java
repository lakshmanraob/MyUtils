package my.util.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import my.util.app.R;
import my.util.app.fragments.LoginFragment;
import my.util.app.fragments.PayAccountFragment;
import my.util.app.utils.StringUtils;

/**
 * Created by labattula on 27/04/17.
 */

public class AuthActivity extends BaseActivity {

    ViewPager authViewPager;
    AuthPagerAdapter pagerAdapter;

    TabLayout authTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authViewPager = (ViewPager) findViewById(R.id.authPager);
        pagerAdapter = new AuthPagerAdapter(getSupportFragmentManager());
        authViewPager.setAdapter(pagerAdapter);

        authViewPager.addOnPageChangeListener(onPageChangeListener);

        authTabLayout = (TabLayout) findViewById(R.id.auth_tab_layout);
        if (authTabLayout != null) {
            authTabLayout.setupWithViewPager(authViewPager);

            for (int i = 0; i < authTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = authTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            authTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
    }

    private String getAuthPageTitle(int position) {
        switch (position) {
            case 0:
                return getString(R.string.login_heading);
            case 1:
                return getString(R.string.pay_for_other);
            default:
                return getString(R.string.no_match);
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Toast.makeText(AuthActivity.this, getAuthPageTitle(position), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class AuthPagerAdapter extends FragmentPagerAdapter {

        private int NUM_ITEMS = 2;

        public AuthPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: //login Page
                    return LoginFragment.newInstance(getAuthPageTitle(position), position);
                case 1: //Pay for account
                    return PayAccountFragment.newInstance(getAuthPageTitle(position), position);
            }

            return null;
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(AuthActivity.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.custom_title);
            title.setText(getAuthPageTitle(position));
            ImageView icon = (ImageView) view.findViewById(R.id.custom_icon);
            icon.setVisibility(View.GONE);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getAuthPageTitle(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }
}
