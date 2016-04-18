package com.example.administrator.pet;

import android.app.Activity;
import java.util.Calendar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.floatwindowdemo.R;

/**
 * Created by wujia on 2016/4/15.
 */
public class SettingActivity extends Activity implements OnClickListener{
    SharedPreferences sharedpreferences;    //读数据
    SharedPreferences.Editor editor;
    AlertDialog dialog_name,dialog_birthday,dialog_character;    //对话框
    LinearLayout set_ll_imagelinearlayout,name_linearlayout,birthday_linearlayout,character_linearlayout;
    ImageView set_iv_imageview;
    EditText name_et_editname,character_et_editcharacter,set_et_showname,set_et_showbirthday,set_et_showcharacter;
    //TextView set_et_showname,set_te_showbirthday,set_et_showcharacter;
    Button titlebar_bt_return,titlebar_bt_save,set_bt_birthday;
    //set_bt_character,set_bt_name,name_bt_cannel,name_bt_enter,character_bt_enter,character_bt_cannel;
    String flag;//标志位
    int height,width,pixelColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        set_ll_imagelinearlayout=(LinearLayout)findViewById(R.id.set_ll_imagelinearlayout);
        set_iv_imageview=(ImageView)findViewById(R.id.set_iv_imageview);
        set_et_showname=(EditText)findViewById(R.id.set_et_showname);
        set_et_showbirthday=(EditText)findViewById(R.id.set_et_showbirthday);
        set_et_showcharacter=(EditText)findViewById(R.id.set_et_showcharacter);
        titlebar_bt_return=(Button)findViewById(R.id.titlebar_bt_return);
        titlebar_bt_save=(Button)findViewById(R.id.titlebar_bt_save);
        //set_bt_name=(Button)findViewById(R.id.set_bt_name);
        set_bt_birthday=(Button)findViewById(R.id.set_bt_birthday);
        //set_bt_character=(Button)findViewById(R.id.set_bt_character);
        //为按钮绑定监听器
        titlebar_bt_return.setOnClickListener(this);
        titlebar_bt_save.setOnClickListener(this);
        //set_bt_name.setOnClickListener(this);
        set_bt_birthday.setOnClickListener(this);
        //set_bt_character.setOnClickListener(this);
        //获取SharedPreferences和Editor读写对象
        sharedpreferences=this.getPreferences(MODE_PRIVATE);
        editor=sharedpreferences.edit();
        //解码Bundle
        Bundle bundle=this.getIntent().getExtras();
        flag=bundle.getString("flag");
        //根据标志位获取图片来源以及是指image_linearlayout的背景色
        if(flag.equals("1")){
            set_iv_imageview.setImageResource(R.drawable.pika);
            Bitmap src =  BitmapFactory.decodeResource(getResources(),R.drawable.pika);
            height = src.getHeight();
            width = src.getWidth();
            pixelColor = src.getPixel(width /200, height /200);
            set_ll_imagelinearlayout.setBackgroundColor(pixelColor);
        }
	  if(flag.equals("2")){
            set_iv_imageview.setImageResource(R.drawable.qiao);
	    	Bitmap src =  BitmapFactory.decodeResource(getResources(),R.drawable.qiao);
	        height = src.getHeight();
	        width = src.getWidth();
	        pixelColor = src.getPixel(width/200, height/200);
            set_ll_imagelinearlayout.setBackgroundColor(pixelColor);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        readFromSharedPreferences(flag);
    }

    @Override
    public void onClick(View button) {
        // TODO Auto-generated method stub
        switch(button.getId()){
            case R.id.titlebar_bt_return:
                SettingActivity.this.finish();
                break;
            case R.id.titlebar_bt_save:
                editor.putString("name"+flag,set_et_showname.getText().toString());
                editor.putString("birthday"+flag,set_et_showbirthday.getText().toString());
                editor.putString("character"+flag,set_et_showcharacter.getText().toString());
                editor.commit();
                SettingActivity.this.finish();
                break;
		/*case R.id.set_bt_name:
			LayoutInflater inflater=LayoutInflater.from(this);
			name_linearlayout=(LinearLayout)inflater.inflate(R.layout.name, null);
			dialog_name=new AlertDialog.Builder(this)
			.setView(name_linearlayout)
			.create();
			dialog_name.show();
			name_et_editname=(EditText)name_linearlayout.findViewById(R.id.name_et_editname);
			name_et_editname.setText(sharedpreferences.getString("name"+flag, null));
			name_bt_enter=(Button)name_linearlayout.findViewById(R.id.name_bt_enter);
			name_bt_cannel=(Button)name_linearlayout.findViewById(R.id.name_bt_cannel);
			name_bt_enter.setOnClickListener(this);
			name_bt_cannel.setOnClickListener(this);
			break;
			*/
            case R.id.set_bt_birthday:
                Calendar c=Calendar.getInstance();
                new DatePickerDialog(SettingActivity.this,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        set_et_showbirthday.setText(year+"/"+(month+1)+"/"+day);

                    }
                    //设置初始日期
                }
                        ,c.get(Calendar.YEAR)
                        ,c.get(Calendar.MONTH)
                        ,c.get(Calendar.DAY_OF_MONTH)).show();
                break;
		/*case R.id.set_bt_character:
			LayoutInflater inflater1=LayoutInflater.from(this);
			character_linearlayout=(LinearLayout)inflater1.inflate(R.layout.character, null);
			dialog_character=new AlertDialog.Builder(this)
			.setView(character_linearlayout)
			.create();
			dialog_character.show();
			character_et_editcharacter=(EditText)character_linearlayout.findViewById(R.id.character_et_editcharacter);
			character_et_editcharacter.setText(sharedpreferences.getString("character"+flag, null));
			character_bt_enter=(Button)character_linearlayout.findViewById(R.id.character_bt_enter);
			character_bt_cannel=(Button)character_linearlayout.findViewById(R.id.character_bt_cannel);
			character_bt_enter.setOnClickListener(this);
			character_bt_cannel.setOnClickListener(this);
			break;
			*/
	/*	case R.id.character_bt_enter:
			editor.putString("character"+flag,character_et_editcharacter.getText().toString());
			editor.commit();
			set_tv_showcharacter.setText(character_et_editcharacter.getText().toString());
			dialog_character.dismiss();
			break;
		case R.id.character_bt_cannel:
			dialog_character.dismiss();
			break;
		case R.id.name_bt_enter:
			editor.putString("name"+flag, name_et_editname.getText().toString());
			editor.commit();
			set_tv_showname.setText(name_et_editname.getText().toString());
			dialog_name.dismiss();
			break;
		case R.id.name_bt_cannel:
			dialog_name.dismiss();
			break;
			*/
            default:break;
        }
    }
    public void readFromSharedPreferences(String i){
        String name=sharedpreferences.getString("name"+i, null);
        String birthday=sharedpreferences.getString("birthday"+i, null);
        String character=sharedpreferences.getString("character"+i, null);
        set_et_showname.setText(name);
        set_et_showbirthday.setText(birthday);
        set_et_showcharacter.setText(character);
    }
}
