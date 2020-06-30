package com.example.zhongyitizhi1.ui.notifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zhongyitizhi1.R;
import com.example.zhongyitizhi1.caozuozhinan;
import com.example.zhongyitizhi1.linearinme;
import com.example.zhongyitizhi1.tuling.AboutActivity;
import com.example.zhongyitizhi1.tuling.MainActivity;
import com.example.zhongyitizhi1.ui.news.news1;
import com.example.zhongyitizhi1.ui.news.news2;
import com.example.zhongyitizhi1.ui.news.news3;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView yujingfuwu;
    private ImageView fufeifuwu;
    private ImageView jijing;
    private ImageView kefu;
    ArrayList fragmentList = new ArrayList<Fragment>();
    String[] temp = {"中医","西医","个人通知"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        tabLayout = root.findViewById(R.id.tablayout);
        viewPager = root.findViewById(R.id.viewpager);
        yujingfuwu = root.findViewById(R.id.yujingfuwu);
        fufeifuwu = root.findViewById(R.id.fufeifuwu);
        jijing = root.findViewById(R.id.jijin);
        kefu = root.findViewById(R.id.kefu);
        yujingfuwu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("功能尚在开发，敬请期待");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点击了确定");
                    }
                });
                builder.show();
            }
        });
        fufeifuwu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("功能尚在开发，敬请期待");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点击了确定");
                    }
                });
                builder.show();
            }
        });
        kefu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("功能尚在开发，敬请期待");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点击了确定");
                    }
                });
                builder.show();
            }
        });
        jijing.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("功能尚在开发，敬请期待");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点击了确定");
                    }
                });
                builder.show();
            }
        });
        View view1 = root.findViewById(R.id.jiankangzixun);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent1);
            }
        });
        return root;
    }
    //onViewCreated是在onCreateView执行之后立即执行。搜索了一圈网上的答案，也没个说的清楚的，大概意思理解为：如果要加载静态页面
    //在onCreateView中设计就好了，如果要动态加载一些组件，比如这边的adapter，就需要在onViewCreated中
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fragment中嵌套fragment, Manager需要用(getChildFragmentManager())
        MPagerAdapter mPagerAdapter = new MPagerAdapter(getChildFragmentManager());
        initFragment();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mPagerAdapter);
    }

    private void initFragment() {
        fragmentList.add(new news1());
        fragmentList.add(new news2());
        fragmentList.add(new news3());
    }


    class MPagerAdapter extends FragmentPagerAdapter {


        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return temp[position];
        }
    }
}