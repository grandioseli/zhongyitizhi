package com.example.zhongyitizhi1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhongyitizhi1.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.stephentuso.welcome.WelcomeHelper;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    public static DrawerLayout drawerLayout;//抽屉栏
    private AppBarConfiguration appBarConfiguration1;//顶部栏
    WelcomeHelper welcomeScreen;//欢迎页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //常规
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置欢迎页
        welcomeScreen = new WelcomeHelper(this, DefaultWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);
        //找到toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        //通过这个方法，toolbar就可以使用一切actionbar方法
        setSupportActionBar(toolbar);
//        下面这个方法可以用来设置透明度！
        //toolbar.getBackground().setAlpha(0);
        //找到抽屉布局
        drawerLayout = findViewById(R.id.d1);
        //找到抽屉栏
        NavigationView navigationView = findViewById(R.id.shouyechouti);
        //引入底部菜单栏
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //也就是将每个菜单ID加入顶级目标
        // 顶级目标即表示为回退栈最底位置, 无法再返回, 故可以理解为不需要返回键导航的页面(Toolbar等就不会显示返回箭头)
        appBarConfiguration1 = new AppBarConfiguration.Builder(
                R.id.navigation_health, R.id.navigation_notifications, R.id.navigation_me)
                .setDrawerLayout(drawerLayout)
                .build();
        //管理应用程序导航
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        //设置AppCompatActivity.getSupportActionBar（）返回的ActionBar以与NavController一起使用
        //通过调用此方法，操作栏上的标题将在目标位置更改时自动更新（假设有有效的标签）
        //您提供的AppBarConfiguration控制导航按钮的显示方式。 调用navigationUp（NavController，AppBarConfiguration）处理向上按钮。
// 这里把他注释掉是为了去掉系统自带的标题栏，如果不注掉，会提示空指针，app会崩溃       NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //设置与导航控制器一起使用的工具栏
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration1);
//接受跳转intent
//        Intent intent=getIntent();
//        int bigMake=intent.getIntExtra("bigMake",0);
//        if(bigMake ==1 ) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment,new NotificationsFragment())
//                    .addToBackStack(null)
//                    .commit();
//            NotificationsFragment fragment = new NotificationsFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.nav_host_fragment, fragment).commit();
//        }

}
//欢迎页相关
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }
    //toolbar导航向上（去掉它，抽屉栏将无法使用）
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration1)
                || super.onSupportNavigateUp();
    }
}
