package my.util.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import my.util.app.DataManager;
import my.util.app.R;
import my.util.app.fragments.SignupFragmentFour;
import my.util.app.fragments.SignupFragmentOne;
import my.util.app.fragments.SignupFragmentThree;
import my.util.app.fragments.SignupFragmentTwo;
import my.util.app.utils.UtilDialog;
import my.util.app.utils.Utils;

public class SignUpActivity extends BaseActivity {

    ViewPager signupViewPager;

    TextView cancelText;
    public TextView nextText;

    LinearLayout signupPagerIndicators;

    private static final int DOT_COOUNT = 4;
    private ImageView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupViewPager = (ViewPager) findViewById(R.id.signup_pager);

        SignupPageAdapter adapter = new SignupPageAdapter(getSupportFragmentManager());
        signupViewPager.setAdapter(adapter);
        signupViewPager.addOnPageChangeListener(onPageChangeListener);

        cancelText = (TextView) findViewById(R.id.signup_cancel);
        nextText = (TextView) findViewById(R.id.signup_next);

        cancelText.setOnClickListener(onClickListener);
        nextText.setOnClickListener(onClickListener);

        signupPagerIndicators = (LinearLayout) findViewById(R.id.signup_pager_indicators);

        setUiPageViewController();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_cancel:
                    if (signupViewPager.getCurrentItem() == 0) {
                        finish();
                    } else {
                        signupViewPager.setCurrentItem(signupViewPager.getCurrentItem() <
                                0 ? DOT_COOUNT : signupViewPager.getCurrentItem() - 1);
                    }
                    break;
                case R.id.signup_next:
                    if (signupViewPager.getCurrentItem() == 3) {
                        UtilDialog.showSubmitDialog(SignUpActivity.this,
                                R.layout.reg_submit_dialog, R.string.reg_submit_message, false, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });
                    } else if (signupViewPager.getCurrentItem() == 0 && !DataManager.getInstance(SignUpActivity.this).isAccountDetailsConfirmed()) {
                        Utils.fetchSignUpDetails(SignUpActivity.this);
                    } else {
                        DataManager.getInstance(SignUpActivity.this).setAccountDetailsConfirmed(false);
                        signupViewPager.setCurrentItem(signupViewPager.getCurrentItem() >
                                DOT_COOUNT ? 0 : signupViewPager.getCurrentItem() + 1);
                    }
                    break;
            }
        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            resetViewPagerIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void resetViewPagerIndicator(int selected) {
        for (int i = 0; i < DOT_COOUNT; i++) {
            if (i == selected) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.signup_selected_dot, null));
            } else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.signup_un_selected_dot, null));
            }
        }

        if (selected > 0) {
            cancelText.setText(getString(R.string.back_str));
            if (selected == 3) {
                nextText.setText(getString(R.string.submit));
            } else {
                nextText.setText(getString(R.string.next_str));
            }
        } else {
            cancelText.setText(getString(R.string.cancel_str));
        }
    }

    private void setUiPageViewController() {

        dots = new ImageView[DOT_COOUNT];

        for (int i = 0; i < DOT_COOUNT; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.signup_un_selected_dot, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            signupPagerIndicators.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.signup_selected_dot, null));
    }

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
            switch (position) {
                case 0:
                    return SignupFragmentOne.newInstance("Position." + position, position);
                case 1:
                    return SignupFragmentTwo.newInstance("Position." + position, position);
                case 2:
                    return SignupFragmentThree.newInstance("Position." + position, position);
                case 3:
                    return SignupFragmentFour.newInstance("Position." + position, position);
                default:
                    return SignupFragmentOne.newInstance("Position." + position, position);
            }

        }

        @Override
        public int getCount() {
            return DOT_COOUNT;
        }
    }
}
