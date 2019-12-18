package com.example.zhongyitizhi1.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.zhongyitizhi1.R;
import com.example.zhongyitizhi1.caozuozhinan;
import com.example.zhongyitizhi1.linearinme;
import com.example.zhongyitizhi1.shezhi;
import com.example.zhongyitizhi1.tizhi;
import com.google.android.material.snackbar.Snackbar;

public class MeFragment extends Fragment {

    private MeViewModel meViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        View view1 = root.findViewById(R.id.caozuozhinan);
        view1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), caozuozhinan.class);
                startActivity(intent1);
            }
        });
        View view2 = root.findViewById(R.id.wodetizhi);
        view2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent2 = new Intent(getActivity(), tizhi.class);
                startActivity(intent2);
            }
        });
        View view3 = root.findViewById(R.id.shezhi);
        view3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent3 = new Intent(getActivity(), shezhi.class);
                startActivity(intent3);
            }
        });
        View view4 = root.findViewById(R.id.linearinme);
        view4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent4 = new Intent(getActivity(), linearinme.class);
                startActivity(intent4);
            }
        });
//        final TextView textView = root.findViewById(R.id.text_me);
//        meViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//         final Button button = root.findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        return root;
    }
}