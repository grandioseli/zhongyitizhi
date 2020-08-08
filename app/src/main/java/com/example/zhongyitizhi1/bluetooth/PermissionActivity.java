package com.example.zhongyitizhi1.bluetooth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.zhongyitizhi1.R;

public class PermissionActivity extends AppCompatActivity implements PermissionInterface{

    private PermissionHelper mPermissionHelper;
    private BlueDeviceListFragment mBlueToothListFragment;
    private String mBlueToothMac = "";
    public static final String EXTRA_CONNECTED_MAC = "com.example.zhongyitizhi1.blueTooth.connectedmac";
    public static final String EXTRA_GET_CONNECTED_MAC = "com.example.zhongyitizhi1.blueTooth.get_connectedmac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBlueToothMac = (String) getIntent().getStringExtra(EXTRA_CONNECTED_MAC);
        setContentView(R.layout.activity_permission);
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbarbluetoothconnection);
        setSupportActionBar(myChildToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        //初始化并发起权限申请
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();

    }
    @Override
    protected void onPause(){
        super.onPause();
        setAnswerShownResult(mBlueToothMac);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)){
            //权限请求结果，并已经处理了该回调

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        //权限请求用户已经全部允许
        initViews();
    }

    @Override
    public void requestPermissionsFail() {
        //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
        Context context = getApplicationContext();
        CharSequence text = "蓝牙相关权限被禁止!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

    private void initViews(){
        //已经拥有所需权限，开始执行一次蓝牙设备扫描
       // fragment管理器
        FragmentManager fm = getSupportFragmentManager();
        //找到一个fragment
        Fragment fragment = fm.findFragmentById(R.id.fragment_bluetoothlist);
        if (mBlueToothListFragment == null){
            mBlueToothListFragment = new BlueDeviceListFragment();
            fm.beginTransaction().add(R.id.fragment_bluetoothlist,mBlueToothListFragment).commit();
        }
        mBlueToothListFragment.searchDevice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bluetoothconnection, menu);
        MenuItem searchItem = menu.findItem(R.id.bluetoothrefresh);
        Button mrefresh= (Button) searchItem.getActionView();

        // Configure the search info and add any event listeners...
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bluetoothrefresh:
                //重新扫描蓝牙设备

                ClientManager.getClient().stopSearch();
                mBlueToothListFragment.searchDevice();
                return true;
            case R.id.bluetoothdisconnected:
                //断开蓝牙连接
                MyBleService.getInstance().disConnect();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    public static Intent newIntent(Context packageContext, String mac){
        Intent intent = new Intent(packageContext,PermissionActivity.class);
        intent.putExtra(EXTRA_CONNECTED_MAC,mac);
        return intent;
    }
    private void setAnswerShownResult(String connectedMac){
        Intent data  = new Intent();
        data.putExtra(EXTRA_GET_CONNECTED_MAC,connectedMac);
        setResult(RESULT_OK,data);
    }
    public static String getConnectedMac(Intent intent){
        return  intent.getStringExtra(EXTRA_GET_CONNECTED_MAC);
    }
}
