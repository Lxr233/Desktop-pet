package com.example.administrator.pet;

/**
 * Created by Lxr on 2016/4/19.
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
import com.example.administrator.pet.view.PetSelect;


public class FirstFragment extends Fragment {
    ToolbarView first_toolbarview;
    private PetSelect first,second,third,fourth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView=inflater.inflate(R.layout.fragment_first,container, false);
        first_toolbarview = (ToolbarView)rootView.findViewById(R.id.first_toolbarview);
        first = (PetSelect)rootView.findViewById(R.id.select_1);
        second = (PetSelect)rootView.findViewById(R.id.select_2);
        third = (PetSelect)rootView.findViewById(R.id.select_3);
        fourth = (PetSelect)rootView.findViewById(R.id.select_4);

        init();
        setListener();


        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        first.setName(sharedPreferences.getString("name1", "皮卡"));
        first.setCheck(sharedPreferences.getBoolean("isFirstOn", false));

        if(sharedPreferences.getBoolean("isSecondUnlock",false)){
            second.setName(sharedPreferences.getString("name2", "鳄鱼"));
            second.setCheck(sharedPreferences.getBoolean("isSecondOn",false));
        }
    }

    private void init(){
        sharedPreferences = getActivity().getSharedPreferences("pet", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        second.setenabled(false);
        third.setenabled(false);
        fourth.setenabled(false);
        first.setPetImg(R.drawable.pika);
        if(!sharedPreferences.getBoolean("isSecondUnlock",false))
            second.setPetImg(R.drawable.kong_unlock);
        else
            second.setPetImg(R.drawable.kong);
        third.setPetImg(R.drawable.qiao_unlock);
        fourth.setPetImg(R.drawable.v_unlock);

    }

    private void setListener(){
        first.setEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("flag", "1");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });



        second.setEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("flag", "2");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    //要获取Activity中的资源，就必须等Acitivity创建完成以后，所以必须放在onActivityCreated()回调函数中
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        first.setSwitchListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!sharedPreferences.getBoolean("isFirstOn", false)) {
                        editor.putBoolean("isFirstOn", true);
                        editor.putBoolean("isSecondOn", false);
                        editor.commit();
                        System.out.println(sharedPreferences.getBoolean("isFirstOn", false));
                        Intent intent = new Intent(getActivity(), FloatWindowService.class);
                        getActivity().startService(intent);
                        getActivity().finish();
                    }
                } else {
                    editor.putBoolean("isFirstOn", false);
                    editor.commit();

                    Intent intent = new Intent(getActivity(), FloatWindowService.class);
                    getActivity().stopService(intent);
                }
            }
        });

        second.setSwitchListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!sharedPreferences.getBoolean("isSecondOn", false)) {
                        editor.putBoolean("isFirstOn", false);
                        editor.putBoolean("isSecondOn", true);
                        editor.commit();
                        Intent intent = new Intent(getActivity(), FloatWindowService.class);
                        getActivity().startService(intent);
                        getActivity().finish();
                    }
                }
                else{
                    editor.putBoolean("isSecondOn",false);
                    editor.commit();
                    Intent intent = new Intent(getActivity(), FloatWindowService.class);
                    getActivity().stopService(intent);
                }
            }
        });

    }

}
