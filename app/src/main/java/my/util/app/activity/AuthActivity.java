package my.util.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import my.util.app.R;
import my.util.app.fragments.LoginFragment;
import my.util.app.fragments.PayAccountFragment;

/**
 * Created by labattula on 27/04/17.
 */

public class AuthActivity extends BaseActivity {

    ViewPager authViewPager;
    AuthPagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_auth);

        authViewPager = (ViewPager) findViewById(R.id.authPager);
        pagerAdapter = new AuthPagerAdapter(getSupportFragmentManager());
        authViewPager.setAdapter(pagerAdapter);

        authViewPager.addOnPageChangeListener(onPageChangeListener);

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
