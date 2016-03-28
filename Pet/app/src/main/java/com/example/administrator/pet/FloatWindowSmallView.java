package com.example.administrator.pet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.floatwindowdemo.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/3/16.
 */
public class FloatWindowSmallView extends LinearLayout {

    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    /**
     * 记录当前手指是否按下
     */
    private boolean isPressed;

    //记录宠物当前是否贴边隐藏
    private boolean isHide;

    //宠物控件
    private ImageView img;

    //宠物图像的宽高
    private int imgWidth;

    private int imgHeigth;

    /**
     * 获取屏幕的高度宽度
    */
    private int screenHeight,screenWidth;

    //动画的定义，定义成全局以便在事件回调的时候能对动画进行操作
    private ValueAnimator anim;
    private AnimationDrawable runFrame;
    private ValueAnimator runAnim;



    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        View view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        img = (ImageView) findViewById(R.id.img_small);
        imgWidth = img.getLayoutParams().width;
        imgHeigth = img.getLayoutParams().height;
        img.setBackgroundResource(R.drawable.pika);

        //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - getStatusBarHeight();

        isHide = false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                isPressed = true;
                if(anim!=null){
                    anim.cancel();
                    runAnim.cancel();
                    runFrame.stop();
                }
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                updateViewStatus();
                break;
            case MotionEvent.ACTION_MOVE:
                isHide = false;
                if(anim!=null){
                    anim.cancel();
                    runAnim.cancel();
                    runFrame.stop();
                }
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                updateViewStatus();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                isPressed = false;
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    openBigWindow();
                }
                else if (xInScreen < screenWidth*1/4){  //当在这个位置放手时宠物贴边隐藏
                    hideLeft();
                }
                else if(xInScreen > screenWidth*3/4){
                    hideRight();
                }
                else {
                    isHide = false;
                    startAnimation();
                    updateViewStatus();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void hideLeft() {
        isHide = true;
        img.setBackgroundResource(R.drawable.pika_hide_left);
        mParams.x = 0;
        mParams.y = (int)yInScreen;
        windowManager.updateViewLayout(this, mParams);
    }

    private void hideRight() {
        isHide = true;
        img.setBackgroundResource(R.drawable.pika_hide_right);
        mParams.x = screenWidth ;
        mParams.y = (int)yInScreen;
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 更新View的显示状态，判断是否拎起。
     */
    private void updateViewStatus() {
        if(isHide&&isPressed) {
            if(xInScreen>screenWidth*1/2)
                img.setBackgroundResource(R.drawable.pika_show_right);
            else
                img.setBackgroundResource(R.drawable.pika_show_left);
        }
        else if (isPressed&&!isHide ) {
            img.setBackgroundResource(R.drawable.pika_take);
            mParams.x = (int)(xInScreen -2* imgWidth/3);
            mParams.y = (int)yInScreen;
            windowManager.updateViewLayout(this, mParams);

        } else if (!isPressed) {
            img.setBackgroundResource(R.drawable.pika);
        }
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - imgWidth/3*2);
        mParams.y = (int) (yInScreen );
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallWindow(getContext());
    }

    private void startAnimation(){
        float startY = yInScreen;
        float endY = yInScreen +200f;
        if(yInScreen+200.0+imgHeigth>screenHeight){
            endY = screenHeight - imgHeigth;
        }

        final float startX = mParams.x;
        final float endX ;

        //判断下落位置
        if(xInScreen > screenWidth/2) {
            endX = screenWidth - imgWidth;
        }
        else{
            endX = 0 ;
        }


        anim = ValueAnimator.ofObject(new animEvaluator(), startY, endY);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mParams.y = (int)(float) animation.getAnimatedValue();
                windowManager.updateViewLayout(FloatWindowSmallView.this, mParams);
            }
        });
        //当下落结束时播放帧动画
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                //帧动画播放
                if(endX == 0)
                    img.setBackgroundResource(R.drawable.walk_left);
                else
                    img.setBackgroundResource(R.drawable.walk_right);
                runFrame = (AnimationDrawable) img.getBackground();
                runFrame.start();

                //属性动画控制位移
                runAnim = ValueAnimator.ofObject(new animEvaluator(), startX, endX);
                runAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mParams.x = (int) (float) animation.getAnimatedValue();

                        windowManager.updateViewLayout(FloatWindowSmallView.this, mParams);
                    }
                });
                runAnim.setDuration(1000);
                runAnim.start();
                runAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        runFrame.stop();
                        img.setBackgroundResource(R.drawable.pika);
                    }
                });
            }
        });
        anim.setInterpolator(new AccelerateInterpolator(4f));
        anim.setDuration(500);
        anim.start();
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}