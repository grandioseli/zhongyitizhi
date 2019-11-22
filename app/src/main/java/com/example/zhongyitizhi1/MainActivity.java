package com.example.zhongyitizhi1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);//引入底部菜单栏
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //也就是将每个菜单ID加入顶级目标
        // 顶级目标即表示为回退栈最底位置, 无法再返回, 故可以理解为不需要返回键导航的页面(Toolbar等就不会显示返回箭头)
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_health, R.id.navigation_notifications, R.id.navigation_me)
                .build();
        //管理应用程序导航
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //设置AppCompatActivity.getSupportActionBar（）返回的ActionBar以与NavController一起使用
        //通过调用此方法，操作栏上的标题将在目标位置更改时自动更新（假设有有效的标签）
        //您提供的AppBarConfiguration控制导航按钮的显示方式。 调用navigationUp（NavController，AppBarConfiguration）处理向上按钮。
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //设置与导航控制器一起使用的工具栏
        NavigationUI.setupWithNavController(navView, navController);
    }

}
