package com.example.zhongyitizhi1.tuling;

import de.devland.esperandro.SharedPreferenceActions;
import de.devland.esperandro.SharedPreferenceMode;
import de.devland.esperandro.annotations.SharedPreferences;


@SharedPreferences(name = "settings", mode = SharedPreferenceMode.PRIVATE)
public interface SettingsSharedPreferences extends SharedPreferenceActions {

    boolean isReceivePush();
    void isReceivePush(boolean isReceivePush);
}
