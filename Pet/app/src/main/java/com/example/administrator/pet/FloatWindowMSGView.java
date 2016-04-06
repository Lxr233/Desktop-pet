package com.example.administrator.pet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.floatwindowdemo.R;

/**
 * Created by Administrator on 2016/4/3.
 */
public class FloatWindowMSGView extends RelativeLayout {
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

    private View view;

    private TextView content,title;



    public FloatWindowMSGView(final Context context ,String msg ,boolean isWeChat ,boolean isLeft) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(isLeft){
            LayoutInflater.from(context).inflate(R.layout.float_msg_left, this);
            view = findViewById(R.id.msg_layout_left);
            title = (TextView)findViewById(R.id.msg_title_left);
            content = (TextView)findViewById(R.id.msg_content_left);
        }
        else{
            LayoutInflater.from(context).inflate(R.layout.float_msg_right, this);
            view = findViewById(R.id.msg_layout_right);
            title = (TextView)findViewById(R.id.msg_title_right);
            content = (TextView)findViewById(R.id.msg_content_right);
        }

        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;


        if(isWeChat){
            title.setText("微信消息");
        }
        else{
            title.setText("闹钟提醒");
        }
        content.setText(msg);
    }


    public void setParams(WindowManager.LayoutParams params,int x , int y){
        mParams = params;
        mParams.x=x;
        mParams.y=y;
        windowManager.updateViewLayout(this, mParams);

    }


}
