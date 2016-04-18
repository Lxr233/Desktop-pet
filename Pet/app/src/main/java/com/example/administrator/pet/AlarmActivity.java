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
		//�������ݿ�򿪰��������
		dbhelper=new MyDatabaseHelper(AlarmActivity.this, "alarm_task_manager",null, 1);
		c=Calendar.getInstance();
		//��ȡSQLDatabase���ݿ����
		//db=dbhelper.getReadableDatabase();
		View tab_date=this.getLayoutInflater().inflate(R.layout.tabwidget, null);
		TextView tv_date=(TextView)tab_date.findViewById(R.id.tab_label);
		tv_date.setText("日期");
		View tab_time=this.getLayoutInflater().inflate(R.layout.tabwidget, null);
		TextView tv_time=(TextView)tab_time.findViewById(R.id.tab_label);
		tv_time.setText("时间");
		//��ȡ��activity�����TabHost����
		tabhost=(TabHost)findViewById(R.id.tabhost);
		tabhost.setup();
		//������һ��Tabҳ
		TabSpec tab1=tabhost.newTabSpec("tab1").setIndicator(tab_date).setContent(R.id.alarmset_ll_tab01);
		tabhost.addTab(tab1);
		//�����ڶ���Tabҳ
		TabSpec tab2=tabhost.newTabSpec("tab2").setIndicator(tab_time).setContent(R.id.alarmset_ll_tab02);
		tabhost.addTab(tab2);
		tabhost.setCurrentTabByTag("tab1"); // ���õ�һ�δ�ʱĬ����ʾ�ı�ǩ
		updateTab(tabhost);//��ʼ��Tab����ɫ�����������ɫ
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
		//ΪCalendarView���¼�������
		calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView arg0, int year, int month,
					int day) {
				// TODO Auto-generated method stub
				c.setTimeInMillis(System.currentTimeMillis());
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month);
				c.set(Calendar.DAY_OF_MONTH, day);
			}
		});
		//Ϊʱ��ѡ����TimePicker��ʱ�������
		timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int hour, int minute) {
				// TODO Auto-generated method stub
				//c.setTimeInMillis(System.currentTimeMillis());
				c.set(Calendar.HOUR, hour);
				c.set(Calendar.MINUTE, minute);
			}
		});
		
	}
	 private void updateTab(final TabHost tabhost) {
		    for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
		    View view = tabhost.getTabWidget().getChildAt(i);
		    if (tabhost.getCurrentTab() == i) {//ѡ��
		    view.setBackgroundColor(Color.GREEN);
		    } else {//��ѡ��
		    	view.setBackgroundColor(this.getResources().getColor(R.color.sweetblue));//��ѡ��ı���
		    }
		    }
	 }
	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch(button.getId()){
		case R.id.alarmset_bt_back:
			//������һ��Activity���߼�����
			finish();
			break;
		case R.id.alarmset_bt_save:
			/*���ӹ���ʵ��
			 *1.AlarmManagerע�Ὺ�����ӣ����͹㲥
			 *2.���������¼������ݿ�
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
			String date=c.get(Calendar.YEAR)+"��"+(c.get(Calendar.MONTH)+1)+"��"+c.get(Calendar.DAY_OF_MONTH)+"��";
			String time=c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE);
			Bundle bundle=new Bundle();
			//��װҪ���͵�����
			bundle.putString("task", task);
			bundle.putString("date", date);
			bundle.putString("time", time);
			//补充，跳转到的组件
			Intent intent=new Intent();
			//��Bundle�����װ��Intent���Ա��ڲ�ͬ����䴫������
			intent.putExtras(bundle);
			//����PendingIntent����
			PendingIntent pi=PendingIntent.getActivity(this, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//��ȡϵͳ���ӷ���
			am=(AlarmManager)getSystemService(ALARM_SERVICE);
			//��������
			am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
			//count�������������ݣ��ύ�޸�
			count++;
			editor.putInt("cuont", count);
			editor.commit();
			//�����ݲ������ݿ�
			dbhelper.getReadableDatabase().execSQL("insert into alarm_task values(null,?,?,?,"+count+")", new String[]{task,date,time});
			Toast.makeText(AlarmActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
			/*һ.����ɾ��
			 //��������id�������ݳ�������ҳ���Ӧ���ӵ�PendingIntent���,Ȼ���������ݿ�����ɾ����Ӧ���Ӽ�¼
			 String SELECT_SQL="select count from alarm_task where _id="+id;
			 Cursor cursor=dbhelper.getReadableDatabase().rawQuery(SELECT_SQL,null);
			 int PID=cursor.getInt("count");
			 String DELETE_SQL="delete from alarm_task where _id="+id;
			 //��Я�����ݵ�Intent����
			 Intent intent=new Intent(AlarmActivity.this,TestActivity.class);
			 //����PendingIntent����
			 PendingIntent pi=PendingIntent.getActivity(this, PID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//��ȡϵͳ���ӷ���
			 am=(AlarmManager)getSystemService(ALARM_SERVICE);
			 //ɾ��ָ��pi��Ӧ������ʱ��
			 am.cannel(pi);
			 */
			
			/*��.�����޸�
			 //��������id�������ݳ�������ҳ���Ӧ���ӵ�PendingIntent���,Ȼ���������ݿ������޸Ķ�Ӧ���Ӽ�¼
			 String SELECT_SQL="select count from alarm_task where _id="+id;
			 Cursor cursor=dbhelper.getReadableDatabase().rawQuery(SELECT_SQL,null);
			 int PID=cursor.getInt("count");
			 //�˴���Ҫ��ȡ�޸ĺ�����ӵ��¼�task������date��ʱ��time
			 //����������������ɣ�������·�װCalendar c�����ʱ��
			 String UPDATE_SQL="update from alarm_task where _id="+id+"set task="+"'"+task+"'"+",date1="+"'"+date+"'"+",time="+"'"+time+"'";
			 dbhelper.getReadableDatabase().execSQL(UPDATE_SQL);
			 Bundle bundle=new Bundle();
			//��װ���ĺ�Ҫ���͵�����
			 bundle.putString("task", task);
			 bundle.putString("date", date);
			 bundle.putString("time", time);
			 Intent intent=new Intent(AlarmActivity.this,TestActivity.class);
			//��Bundle�����װ��Intent���Ա��ڲ�ͬ����䴫������
			 intent.putExtras(bundle);
			//����PendingIntent����(!���Ҫ����FLAGλ)
			 PendingIntent pi=PendingIntent.getActivity(this, PID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			//��ȡϵͳ���ӷ���
			 am=(AlarmManager)getSystemService(ALARM_SERVICE);
			//���¿�������
			 am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
			 */
			
		default:break;
	
		}
	} 

	@Override
	public void onDestroy(){
		super.onDestroy();
		//�˳�����ʱ�ر�SQLiteDatabase
		if(dbhelper !=null){
			dbhelper.close();
		}
	}
}
