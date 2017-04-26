package my.util.app.activity;

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

import my.util.app.R;
import my.util.app.fragments.PageFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabLayout;

    private int[] mTabsIcons = {
            R.drawable.ic_fav_selector,
            R.drawable.ic_place_selector,
            R.drawable.ic_recents_selector,
            R.drawable.ic_chat_selector
    };

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

        private final String[] page_title = {"android", "IOT", "IOS"};

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }


        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.custom_title);
            title.setText(page_title[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.custom_icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PageFragment.newInstance(page_title[position]);
                case 1:
                    return PageFragment.newInstance(page_title[position]);
                case 2:
                    return PageFragment.newInstance(page_title[position]);
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
