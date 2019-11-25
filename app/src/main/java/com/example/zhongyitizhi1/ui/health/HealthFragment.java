package com.example.zhongyitizhi1.ui.health;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.zhongyitizhi1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class HealthFragment extends Fragment {

    private HealthViewModel healthViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState ) {
        //获取healthviewmodel对象，估计这个of就把该viewmodel和该fragment关联起来了
        healthViewModel =
                ViewModelProviders.of(this).get(HealthViewModel.class);
        //装载xml布局，最后一个参数的意思是仅作用于子类而不作用于父类
        View root = inflater.inflate(R.layout.fragment_health, container, false);
        //定义一个textview，然后将healthviewmodel读取他的text然后监听其是否改变，如果改变则将textview设为string s
        final TextView textView = root.findViewById(R.id.text_health);
        healthViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        //返回的 View 必须是片段布局的根视图
        return root;

    }
}