package com.example.zhongyitizhi1.expandable;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zhongyitizhi1.R;
import com.google.android.material.tabs.TabLayout;

public class expandable extends AppCompatActivity {
    private static final String[] TAB_TITLES = {
            "男生问卷",
            "女生问卷"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new simpleAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }

    private class simpleAdapter extends FragmentPagerAdapter {
        public simpleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
//                    l1.setBackgroundResource(R.drawable.yuanjiaojuzhen5);
//                    l2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_nansheng));
                    return new maleRecyclerViewFragment();
                case 1:
//                    l1.setBackgroundResource(R.drawable.yuanjiaojuzhen6);
//                    l2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_nvsheng));
                    return new femaleRecyclerViewFragment();
            }
            throw new IllegalStateException("尚未添加fragment " + position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }
    }
    //重写这个方法就可以实现toolbar的返回，目前尚未看懂
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
