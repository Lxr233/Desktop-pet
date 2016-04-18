package com.example.administrator.pet;

/**
 * Created by wujia on 2016/4/4.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.demo.floatwindowdemo.R;

import com.example.administrator.pet.view.BackButton;


public class FirstFragment extends Fragment {
    ToolbarView first_toolbarview;
    RelativeLayout fragment_first__more1,fragment_first__more2,unlock;
    private Switch pet_switch1,pet_switch2,pet_switch3,pet_switch4;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView=inflater.inflate(R.layout.fragment_first,container, false);
        first_toolbarview = (ToolbarView)rootView.findViewById(R.id.first_toolbarview);
        fragment_first__more1 = (RelativeLayout)rootView.findViewById(R.id.fragment_first__more1);
        fragment_first__more2 = (RelativeLayout)rootView.findViewById(R.id.fragment_first__more2);
        unlock = (RelativeLayout)rootView.findViewById(R.id.qiaoba);
        first_toolbarview.setToolbar_text("形象选择");
        first_toolbarview.settoolbar_more_Visibility(View.GONE);
        first_toolbarview.settoolbar_relative_Visibility(View.GONE);
        fragment_first__more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("wujiawen+++S1");
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("flag", "1");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        fragment_first__more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("flag", "2");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        pet_switch1=(Switch)rootView.findViewById(R.id.pet_switch1);
        pet_switch2=(Switch)rootView.findViewById(R.id.pet_switch2);
        pet_switch3=(Switch)rootView.findViewById(R.id.pet_switch3);
        pet_switch4=(Switch)rootView.findViewById(R.id.pet_switch4);
        pet_switch3.setClickable(false);
        pet_switch4.setClickable(false);
        sharedPreferences = getActivity().getSharedPreferences("unlock", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        int i = sharedPreferences.getInt("count",0);
        if(i == 1) {
            pet_switch2.setClickable(true);
            unlock.setBackgroundResource(R.drawable.qiao_unlock);
            pet_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        pet_switch2.setChecked(false);
                    }
                }
            });
            pet_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        pet_switch1.setChecked(false);
                    }
                }
            });
        }
        else {
            pet_switch2.setClickable(false);
        }


        return rootView;
    }
}
