package my.util.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import my.util.app.R;
import my.util.app.fragments.LoginFragment;
import my.util.app.fragments.SignupFragment;

/**
 * Created by labattula on 28/04/17.
 */

public class SignUpActivity extends BaseActivity {

    ViewPager signupViewPager;

    TextView cancelText;
    TextView nextText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupViewPager = (ViewPager) findViewById(R.id.signup_pager);

        SignupPageAdapter adapter = new SignupPageAdapter(getSupportFragmentManager());
        signupViewPager.setAdapter(adapter);

        cancelText = (TextView) findViewById(R.id.signup_cancel);
        nextText = (TextView) findViewById(R.id.signup_next);

        cancelText.setOnClickListener(onClickListener);
        nextText.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_cancel:
                    finish();
                    break;
                case R.id.signup_next:
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private class SignupPageAdapter extends FragmentPagerAdapter {

        public SignupPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SignupFragment.newInstance("Position." + position, position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
