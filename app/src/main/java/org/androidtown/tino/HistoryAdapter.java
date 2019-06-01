package org.androidtown.tino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    public LayoutInflater inflater;
    private ArrayList<HistoryItem> data;
    private int layout;

    public HistoryAdapter(Context context,int layout,ArrayList<HistoryItem> data){
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }
    public int getCount(){
        return data.size();
    }
    public String getItem(int position){
        return data.get(position).getName();
    }
    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        HistoryItem historyItem=data.get(position);

        TextView name=(TextView)convertView.findViewById(R.id.s_Name);
        name.setText(historyItem.getName());
        TextView hour=(TextView)convertView.findViewById(R.id.sHour);
        hour.setText(historyItem.getHour());
        TextView min=(TextView)convertView.findViewById(R.id.sMinute);
        min.setText(historyItem.getMinute());

        return convertView;

    }
}
