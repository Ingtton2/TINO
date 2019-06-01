package org.androidtown.tino;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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

        final TinoDB helper =new TinoDB(getContext());
        db = helper.getReadableDatabase();
        sql = "Select * from tino where time>=1 and do =1 ORDER BY id DESC;";

        Cursor cursor = db.rawQuery(sql, null);
        final int count=cursor.getCount();
        try {
            if (cursor != null) {
                for (int i = 0; i < count; i++) {
                    cursor.moveToNext();
                    String task = cursor.getString(cursor.getColumnIndex("name"));
                    arraylist.add(task);
                    Log.d("sls", "name: " + task );
                }
            }
        } finally {
            db.close();
            cursor.close();
        }

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arraylist);
        listView.setAdapter(Adapter);

        final BmDB bookmark = new BmDB(getContext());
        db2 = bookmark.getReadableDatabase();
        sql2 = "select d_hour, d_min from bookmark;";

        Cursor cursor2 = db2.rawQuery(sql2, null);
        final int count2 = cursor2.getCount();
        try {
            if (cursor2!=null){
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

        SQLiteDatabase db3;
        String sql3;

        final BmDB bookmark2 = new BmDB(getContext());
        db3 = bookmark2.getReadableDatabase();
        sql3 = "select name from bookmark;";

        Cursor cursor3 = db3.rawQuery(sql3, null);
        final int count3 = cursor3.getCount();
        try {
            if (cursor3!=null){
                for (int i = 0; i < count3; i++) {
                    cursor3.moveToNext();
                    String Name = cursor3.getString(cursor3.getColumnIndex("name"));
                    name.setText(Name);
                }
            }
        } finally {
            db3.close();
            cursor3.close();
        }

        return layout;
    }
}
