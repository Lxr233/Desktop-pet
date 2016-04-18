package com.example.administrator.pet;

/**
 * Created by wujia on 2016/4/4.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.floatwindowdemo.R;
import com.example.administrator.pet.MyDatabaseHelper;
import com.example.administrator.pet.ToolbarView;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    public ToolbarView second_toolbarview;
    List<String> s1 = new ArrayList<>();
    List<Boolean> B = new ArrayList<>();
    List<String> s2 = new ArrayList<>();
    List<String> s3 = new ArrayList<>();
    List<CheckBox> c = new ArrayList<>();
    List<Integer> NUM = new ArrayList<>();
    MyAdapter myadapter ;
    ListView listview;
    CheckBox list_checkbox;
    private MyDatabaseHelper dbhelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        second_toolbarview = (ToolbarView) rootView.findViewById(R.id.second_toolbarview);
        dbhelper=new MyDatabaseHelper(getActivity(), "alarm_task_manager",null, 1);
        second_toolbarview.setToolbar_text("闹钟提醒");

        second_toolbarview.setmoreclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "更多", Toast.LENGTH_SHORT).show();
                PopupMenu popup = new PopupMenu(getActivity(), second_toolbarview.toolbar_more);
                popup.getMenuInflater().inflate(R.menu.popup_item, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
                                //Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                                int num = 0;
                                NUM.clear();
                                for (int i = 0; i < B.size(); i++) {
                                    if (B.get(i).toString().equals("true")) {
                                        NUM.add(i);
                                        num++;
                                    }
                                }
                                if (num == 0)
                                    Toast.makeText(getActivity(), "请选择删除对象", Toast.LENGTH_SHORT).show();
                                else {
                                    for (int j = 0; j < NUM.size(); j++) {

                                        System.out.println(s1.get(NUM.get(j)));
                                        int n = NUM.get(j);
                                        s1.remove(n);

                                        /*for(int m = 0 ; m <s1.size();m++)
                                        {
                                            Toast.makeText(getActivity(), s1.get(m)+"wujiawen", Toast.LENGTH_SHORT).show();
                                        }*/
                                        s2.remove(n);
                                        s3.remove(n);
                                        B.remove(n);
                                        c.remove(n);
                                        myadapter.notifyDataSetChanged();
                                    }
                                }
                                break;

                            case R.id.edit:
                                //Toast.makeText(getActivity(), "编辑", Toast.LENGTH_SHORT).show();
                                int num1 = 0;
                                for (int i = 0; i < B.size(); i++) {
                                    if (B.get(i).toString().equals("true")) num1++;
                                }
                                if (num1 == 0)
                                    Toast.makeText(getActivity(), "请选择编辑对象", Toast.LENGTH_SHORT).show();
                                else if (num1 > 1)
                                    Toast.makeText(getActivity(), "一次只能编辑一个对象", Toast.LENGTH_SHORT).show();

                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        second_toolbarview.setrelativeclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AlarmActivity.class);
                startActivity(intent);
            }
        });

        listview = (ListView) rootView.findViewById(R.id.activity_main_listview);

        /*s1.add("起床");
        B.add(false);
        s2.add("2016年3月24号");
        s3.add("15:20");

        s1.add("吃饭");
        B.add(true);
        s2.add("2016年4月21号");
        s3.add("19:35");
        */
        String SELECT_ALL="select * from alarm_task";
        Cursor cursor=dbhelper.getReadableDatabase().rawQuery(SELECT_ALL,null);
        while(cursor.moveToNext()){
            s1.add(cursor.getString(1));
            s2.add(cursor.getString(2));
            s3.add(cursor.getString(3));
        }

        //System.out.println(s1.get(1));
        myadapter= new MyAdapter();
        listview.setAdapter(myadapter);

        //listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                if (B.get(i).toString().equals("true")) {
                    c.get(i).setSelected(false);
                    B.set(i, false);
                } else {
                    c.get(i).setSelected(true);
                    B.set(i, true);
                }
                myadapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }
    public class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return s1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View l = inflater.inflate(R.layout.list,null);
            TextView list_up_text = (TextView)l.findViewById(R.id.list_up_text);
            TextView list_down_left_text = (TextView)l.findViewById(R.id.list_down_left_text);
            TextView list_down_right_text = (TextView)l.findViewById(R.id.list_down_right_text);
            list_checkbox = (CheckBox)l.findViewById(R.id.list_checkbox);
            //list_checkbox.setFocusable(false);
            c.add(list_checkbox);
            //System.out.println(position);
            //System.out.println(s1.get(position));
            list_up_text.setText(s1.get(position));
            list_down_left_text.setText(s2.get(position));
            list_down_right_text.setText(s3.get(position));
            list_checkbox.setChecked(B.get(position));
            return l;
        }
    }

}

