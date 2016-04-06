package com.example.administrator.pet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.demo.floatwindowdemo.R;


public class MainActivity extends Activity {


    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startFloatWindow = (Button) findViewById(R.id.start_float_window);
        if(!isEnabled()){
            new AlertDialog.Builder(MainActivity.this).setTitle("是否开启Notification access")//设置对话框标题

                    .setMessage("若想启动微信消息提醒则开启")//设置显示的内容

                    .setPositiveButton("开启", new DialogInterface.OnClickListener() {//添加确定按钮


                        @Override

                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                            // TODO Auto-generated method stub

                            startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));

                        }

                    }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮



                @Override

                public void onClick(DialogInterface dialog, int which) {//响应事件

                    // TODO Auto-generated method stub


                }

            }).show();//在按键响应事件中显示此对话框
        }
        startFloatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, FloatWindowService.class);
                startService(intent);
                finish();
            }
        });
    }
    //判断是否开启了Notification access
    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}