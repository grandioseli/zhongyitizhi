package com.example.zhongyitizhi1.ui.health;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
//ViewModel类是被设计用来以可感知生命周期的方式存储和管理 UI 相关数据，ViewModel中数据会一直存活即使 activity configuration发生变化，比如横竖屏切换的时候
public class HealthViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HealthViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这是健康activity");
    }
    public LiveData<String> getText() {
        return mText;
    }
}