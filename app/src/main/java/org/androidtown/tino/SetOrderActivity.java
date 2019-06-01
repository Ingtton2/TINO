package org.androidtown.tino;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SetOrderActivity extends AppCompatActivity {

    private MyArrayAdapter mAdapter;
    private ListView mListView;
    private boolean mSortable = false;
    private String mDragString;
    private int mPosition = -1;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_order);

        mListView = (ListView) findViewById(R.id.listView);

        mAdapter = new MyArrayAdapter(this, R.layout.order_item);

        mAdapter.add("씻기");
        mAdapter.add("머리말리기");
        //mAdapter.add("밥먹기");
        mAdapter.add("화장");
        mAdapter.add("고데기");
        mAdapter.add("옷고르기");
        // mAdapter.add("모닝똥");
        mAdapter.add("가방챙기기");


        mListView.setAdapter(mAdapter);

        // 大事な所
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (!mSortable) {
                    return false;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        // 現在のポジションを取得し
                        int position = mListView.pointToPosition((int) event.getX(), (int) event.getY());
                        if (position < 0) {
                            break;
                        }
                        // 移動が検出されたら入れ替え
                        if (position != mPosition) {
                            mPosition = position;
                            mAdapter.remove(mDragString);
                            mAdapter.insert(mDragString, mPosition);
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE: {
                        stopDrag();
                        return true;
                    }
                }
                return false;
            }
        });

        btnOk = (Button)findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SetOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startDrag(String string) {
        mPosition = -1;
        mSortable = true;
        mDragString = string;
        mAdapter.notifyDataSetChanged(); // ハイライト反映・解除の為
    }

    public void stopDrag() {
        mPosition = -1;
        mSortable = false;
        mDragString = null;
        mAdapter.notifyDataSetChanged(); // ハイライト反映・解除の為
    }

    static class ViewHolder {
        TextView title;
        ImageView handle;
    }

    public class MyArrayAdapter extends ArrayAdapter<String> {

        private ArrayList<String> mStrings = new ArrayList<String>();
        private LayoutInflater mInflater;
        private int mLayout;

        public MyArrayAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mLayout = textViewResourceId;
        }

        @Override
        public void add(String row) {
            super.add(row);
            mStrings.add(row);
        }

        @Override
        public void insert(String row, int position) {
            super.insert(row, position);
            mStrings.add(position, row);
        }

        @Override
        public void remove(String row) {
            super.remove(row);
            mStrings.remove(row);
        }

        @Override
        public void clear() {
            super.clear();
            mStrings.clear();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            View view = convertView;
            if (view == null) {
                view = mInflater.inflate(this.mLayout, null);
                assert view != null;
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.handle = (ImageView) view.findViewById(R.id.handle);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            final String string = mStrings.get(position);

            holder.title.setText(string);

            // ハンドルタップでソート開始
            holder.handle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        startDrag(string);
                        return true;
                    }
                    return false;
                }
            });

            // ドラッグ行のハイライト
            if (mDragString != null && mDragString.equals(string)) {
                view.setBackgroundColor(Color.parseColor("#9933b5e5"));
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
            }

            return view;
        }
    }

}