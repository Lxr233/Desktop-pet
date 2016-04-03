package com.example.administrator.pet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.demo.floatwindowdemo.R;

/**
 * Created by Administrator on 2016/4/3.
 */
public class FloatWindowMSGView extends FrameLayout {
    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;



    public FloatWindowMSGView(final Context context ,String msg) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_msg, this);
        View view = findViewById(R.id.msg_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        TextView text = (TextView)findViewById(R.id.text_msg);
        text.setText(msg);
    }

    public void setParams(WindowManager.LayoutParams params,int x , int y){
        mParams = params;
        mParams.x=x;
        mParams.y=y;
        windowManager.updateViewLayout(this, mParams);

    }


}
