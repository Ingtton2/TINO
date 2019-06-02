package org.androidtown.tino;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleFragment extends Fragment {
    TextView checkInputHour, checkInputMinute, name;
    ListView listView;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_check_schedule2, container, false);

        ArrayList<String> arraylist = new ArrayList<String>();

        checkInputHour = layout.findViewById(R.id.checkInputHour);
        checkInputMinute = layout.findViewById(R.id.checkInputMinute);
        listView = layout.findViewById(R.id.listView);
        name = layout.findViewById(R.id.name);

        SQLiteDatabase db, db2;
        String sql, sql2;

        CustomAdapter customAdapter = new CustomAdapter(getContext());
        final TinoDB helper = new TinoDB(getContext());
        db = helper.getReadableDatabase();
        sql = "Select name, time from tino where time>=1 and do =1 ORDER BY id DESC;";

        Cursor cursor = db.rawQuery(sql, null);
        final int count = cursor.getCount();
        try {
            if (cursor != null) {
                for (int i = 0; i < count; i++) {
                    cursor.moveToNext();
                    String task = cursor.getString(cursor.getColumnIndex("name"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));

                    customAdapter.addItem(task, time);
                    listView.setAdapter(customAdapter);
                    //arraylist.add(task);
                    Log.d("sls", "name: " + task);
                }
            }
        } finally {
            db.close();
            cursor.close();
        }

        final BmDB bookmark = new BmDB(getContext());
        db2 = bookmark.getReadableDatabase();
        sql2 = "select d_hour, d_min from bookmark;";

        Cursor cursor2 = db2.rawQuery(sql2, null);
        final int count2 = cursor2.getCount();
        try {
            if (cursor2 != null) {
                for (int i = 0; i < count2; i++) {
                    cursor2.moveToNext();
                    String d_hour = cursor2.getString(cursor2.getColumnIndex("d_hour"));
                    String d_min = cursor2.getString(cursor2.getColumnIndex("d_min"));
                    checkInputHour.setText(d_hour);
                    checkInputMinute.setText(d_min);
                }
            }
        } finally {
            db2.close();
            cursor2.close();
        }


        return layout;


    }

    public class CustomAdapter extends BaseAdapter {
        ArrayList<ScheduleData> listViewItemList = null;
        LayoutInflater mInflater = null;

        public CustomAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listViewItemList = new ArrayList<ScheduleData>();
        }

        @Override
        public int getCount() {
            return listViewItemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = mInflater.inflate(R.layout.schedule_item, parent, false);

            TextView textView01 = (TextView) convertView.findViewById(R.id.nameTXT);
            TextView textView02 = (TextView) convertView.findViewById(R.id.timeTXT);
            ScheduleData customData = listViewItemList.get(position);

            textView01.setText(customData.getTxt01());
            textView02.setText(customData.getTxt02());
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return listViewItemList.get(position);
        }

        public void addItem(String txt01, String txt02) {
            ScheduleData customData = new ScheduleData(txt01, txt02);
            listViewItemList.add(customData);
        }
    }
}
