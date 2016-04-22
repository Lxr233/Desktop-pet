package com.example.administrator.pet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Lxr on 2016/4/22.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        //收到闹钟Intent,解封数据
        Bundle bundle=intent.getExtras();
        String task=bundle.getString("task");
        String date=bundle.getString("date");
        String time=bundle.getString("time");
        //封装Intent,以便发送局部广播
        Intent msgrcv = new Intent("Alarm");
        msgrcv.putExtra("task", task);
        msgrcv.putExtra("date", date);
        msgrcv.putExtra("time", time);
        //发送局部广播
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
    }

}