package com.example.zhongyitizhi1.ui.health;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zhongyitizhi1.MainActivity;
import com.example.zhongyitizhi1.R;
import androidx.navigation.Navigation;

import com.example.zhongyitizhi1.lishiqushi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;



public class HealthFragment extends Fragment{

    private HealthViewModel healthViewModel;
    public static DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //获取healthviewmodel对象，估计这个of就把该viewmodel和该fragment关联起来了
        healthViewModel =
                ViewModelProviders.of(this).get(HealthViewModel.class);
        //装载xml布局，最后一个参数的意思是仅作用于子类而不作用于父类
        View root = inflater.inflate(R.layout.fragment_health, container, false);
        View view1 = root.findViewById(R.id.chakanqushi);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), lishiqushi.class);
                startActivity(intent1);
            }
        });
        //定义一个textview，然后将healthviewmodel读取他的text然后监听其是否改变，如果改变则将textview设为string s
//        final TextView textView = root.findViewById(R.id.text_health);
//        healthViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        //设置按钮
//        final Button button = root.findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Toast.makeText(getActivity(), "hello world 2", Toast.LENGTH_SHORT).show();
//            }
//        });
        //返回的 View 必须是片段布局的根视图(返回一个view，该view就是该fragment显示的view)
        return root;
    }
}
