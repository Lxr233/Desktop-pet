package com.example.administrator.pet;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.demo.floatwindowdemo.R;

public class AlarmActivity extends Activity implements OnClickListener{
	//private int year,month,day,hour,minute;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	AlarmManager am;
	private Button bt_back,bt_save;
	private EditText et_task,et_remark;
	private TextView tv_date,tv_time;
	private Calendar c;
	private DatePicker datepicker;
	private TimePicker timepicker;
	private CalendarView calendarview;
	private TabHost tabhost;
	private MyDatabaseHelper dbhelper;
	// private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarmset);
		//创建数据库打开帮助类对象
		dbhelper=new MyDatabaseHelper(AlarmActivity.this, "alarm_task_manager",null, 1);
		c=Calendar.getInstance();
		//获取SQLDatabase数据库对象
		//db=dbhelper.getReadableDatabase();
		View tab_date=this.getLayoutInflater().inflate(R.layout.tabwidget, null);
		TextView tv_date=(TextView)tab_date.findViewById(R.id.tab_label);
		tv_date.setText("日期");
		View tab_time=this.getLayoutInflater().inflate(R.layout.tabwidget, null);
		TextView tv_time=(TextView)tab_time.findViewById(R.id.tab_label);
		tv_time.setText("时间");
		//获取该activity里面的TabHost对象
		tabhost=(TabHost)findViewById(R.id.tabhost);
		tabhost.setup();
		//创建第一个Tab页
		TabSpec tab1=tabhost.newTabSpec("tab1").setIndicator(tab_date).setContent(R.id.alarmset_ll_tab01);
		tabhost.addTab(tab1);
		//创建第二个Tab页
		TabSpec tab2=tabhost.newTabSpec("tab2").setIndicator(tab_time).setContent(R.id.alarmset_ll_tab02);
		tabhost.addTab(tab2);
		tabhost.setCurrentTabByTag("tab1"); // 设置第一次打开时默认显示的标签
		updateTab(tabhost);//初始化Tab的颜色，和字体的颜色
		tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				tabhost.setCurrentTabByTag(tabId);
				updateTab(tabhost);
			}
		});
		calendarview=(CalendarView)findViewById(R.id.alarmset_cv_calendarset);
		timepicker=(TimePicker)findViewById(R.id.alarmset_tp_timeset);
		timepicker.setIs24HourView(true);
		bt_back=(Button)findViewById(R.id.alarmset_bt_back);
		bt_save=(Button)findViewById(R.id.alarmset_bt_save);
		et_task=(EditText)findViewById(R.id.alarmset_et_task);
		et_remark=(EditText)findViewById(R.id.alarmset_et_remark);
		//tv_date=(TextView)findViewById(R.id.tv_date);
		//tv_time=(TextView)findViewById(R.id.tv_time);
		//tv_date.setOnTouchListener(this);
		//tv_time.setOnTouchListener(this);
		bt_back.setOnClickListener(this);
		bt_save.setOnClickListener(this);
		//为CalendarView绑定事件监听器
		calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView arg0, int year, int month,
											int day) {
				// TODO Auto-generated method stub
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month);
				c.set(Calendar.DAY_OF_MONTH, day);
			}
		});
		//为时间选择器TimePicker绑定时间监听器
		timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int hour, int minute) {
				// TODO Auto-generated method stub
				c.setTimeInMillis(System.currentTimeMillis());
				c.set(Calendar.HOUR, hour);
				c.set(Calendar.MINUTE, minute);
				c.set(Calendar.SECOND,0);
				c.set(Calendar.MILLISECOND,0);
			}
		});

	}
	private void updateTab(final TabHost tabhost) {
		for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
			View view = tabhost.getTabWidget().getChildAt(i);
			if (tabhost.getCurrentTab() == i) {//选中
				view.setBackgroundColor(Color.GREEN);
			} else {//不选中
				view.setBackgroundColor(this.getResources().getColor(R.color.sweetblue));//非选择的背景
			}
		}
	}
	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch(button.getId()){
			case R.id.alarmset_bt_back:
				//返回上一个Activity的逻辑代码
				AlarmActivity.this.finish();
				break;
			case R.id.alarmset_bt_save:
			/*闹钟功能实现
			 *1.AlarmManager注册开启闹钟，发送广播
			 *2.保存闹钟事件至数据库
			 */
				//year=c.get(Calendar.YEAR);
				//month=c.get(Calendar.MONTH);
				//day=c.get(Calendar.DAY_OF_MONTH);
				//hour=c.get(Calendar.HOUR);
				//minute=c.get(Calendar.MINUTE);
				preferences=this.getSharedPreferences("Counter", MODE_PRIVATE);
				editor=preferences.edit();
				int count=preferences.getInt("count", 1);
				String task=et_task.getText().toString();
				String date=c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日";
				String time=c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE);
				Bundle bundle=new Bundle();
				//封装要传送的数据
				bundle.putString("task", task);
				bundle.putString("date", date);
				bundle.putString("time", time);
				Intent intent=new Intent(AlarmActivity.this,AlarmReceiver.class);
				//将Bundle对象封装进Intent，以便在不同组件间传递数据
				intent.putExtras(bundle);
				//创建PendingIntent对象
				PendingIntent pi=PendingIntent.getBroadcast(this, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				//获取系统闹钟服务
				am=(AlarmManager)getSystemService(ALARM_SERVICE);
				//开启闹钟
				am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
				//count自增，存入数据，提交修改
				count++;
				editor.putInt("count", count);
				editor.commit();
				//把数据插入数据库
				dbhelper.getReadableDatabase().execSQL("insert into alarm_task values(null,?,?,?,"+count+")", new String[]{task,date,time});
				Toast.makeText(AlarmActivity.this, "闹钟设置成功！", Toast.LENGTH_SHORT).show();
				AlarmActivity.this.finish();
				break;
			/*一.闹钟删除
			 //根据闹钟id现在数据出里面查找出对应闹钟的PendingIntent编号,然后再在数据库里面删除对应闹钟记录
			 String SELECT_SQL="select count from alarm_task where _id="+id;
			 Cursor cursor=dbhelper.getReadableDatabase().rawQuery(SELECT_SQL,null);
			 int PID=cursor.getInt("count");
			 String DELETE_SQL="delete from alarm_task where _id="+id;
			 //无携带数据的Intent对象
			 Intent intent=new Intent(AlarmActivity.this,TestActivity.class);
			 //创建PendingIntent对象
			 PendingIntent pi=PendingIntent.getActivity(this, PID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//获取系统闹钟服务
			 am=(AlarmManager)getSystemService(ALARM_SERVICE);
			 //删除指定pi对应的闹钟时间
			 am.cannel(pi);
			 */

			/*二.闹钟修改
			 //根据闹钟id现在数据出里面查找出对应闹钟的PendingIntent编号,然后再在数据库里面修改对应闹钟记录
			 String SELECT_SQL="select count from alarm_task where _id="+id;
			 Cursor cursor=dbhelper.getReadableDatabase().rawQuery(SELECT_SQL,null);
			 int PID=cursor.getInt("count");
			 //此处需要获取修改后的闹钟的事件task、日期date、时间time
			 //！！！！！！待完成，如何重新封装Calendar c对象的时间
			 String UPDATE_SQL="update from alarm_task where _id="+id+"set task="+"'"+task+"'"+",date1="+"'"+date+"'"+",time="+"'"+time+"'";
			 dbhelper.getReadableDatabase().execSQL(UPDATE_SQL);
			 Bundle bundle=new Bundle();
			//封装更改后要传送的数据
			 bundle.putString("task", task);
			 bundle.putString("date", date);
			 bundle.putString("time", time);
			 Intent intent=new Intent(AlarmActivity.this,TestActivity.class);
			//将Bundle对象封装进Intent，以便在不同组件间传递数据
			 intent.putExtras(bundle);
			//创建PendingIntent对象(!最后要更改FLAG位)
			 PendingIntent pi=PendingIntent.getActivity(this, PID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//获取系统闹钟服务
			 am=(AlarmManager)getSystemService(ALARM_SERVICE);
			//重新开启闹钟
			 am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
			 */

			default:break;

		}
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		//退出程序时关闭SQLiteDatabase
		if(dbhelper !=null){
			dbhelper.close();
		}
	}
}