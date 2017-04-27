package my.util.app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import butterknife.ButterKnife;
import my.util.app.R;
import my.util.app.fragments.ComplaintsFragment;
import my.util.app.fragments.PlaceholderFragment;

public class MainActivity extends BaseActivity {

    TabLayout mTabLayout;

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

    private class MyPageAdapter extends FragmentPagerAdapter {

        private final int PAGE_COUNT = 3;

        private final String[] page_title = {"Recent Bills", "Complaints", "Account Details"};

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }


        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.custom_title);
            title.setText(page_title[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.custom_icon);
            icon.setImageDrawable(getIconDrawable(position));
            return view;
        }

        private Drawable getIconDrawable(int position){
            switch (position) {
                case 0:
                    return new IconDrawable(MainActivity.this, FontAwesomeIcons.fa_list_alt)
                            .colorRes(R.color.white).actionBarSize();
                case 1:
                    return new IconDrawable(MainActivity.this, FontAwesomeIcons.fa_headphones)
                            .colorRes(R.color.white).actionBarSize();
                case 2:
                    return new IconDrawable(MainActivity.this, FontAwesomeIcons.fa_user)
                            .colorRes(R.color.white).actionBarSize();
                default:
                    new IconDrawable(MainActivity.this, FontAwesomeIcons.fa_star_o)
                            .colorRes(R.color.white).actionBarSize();
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PlaceholderFragment.newInstance("Recent Bills", null);
                case 1:
                    return ComplaintsFragment.newInstance("Complaints", null);
                case 2:
                    return PlaceholderFragment.newInstance("Account Details", null);
            }
            return null;
        }

        @Override
        public int getCount() {
            return page_title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return page_title[position];
        }
    }
}
