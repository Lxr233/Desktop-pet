package com.example.administrator.pet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class StartupReceiver extends BroadcastReceiver
{
      SharedPreferences sharedPreferences ;
      SharedPreferences.Editor editor;
      @Override 
      public void onReceive(Context context, Intent Intent)
      {
            sharedPreferences = context.getSharedPreferences("pet", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            if(sharedPreferences.getBoolean("on", true)&&(sharedPreferences.getBoolean("isSecondOn", true)||sharedPreferences.getBoolean("isFirstOn", true))){
                  Intent intent = new Intent(context, FloatWindowService.class);
                  context.startService(intent);
            }

}
}