package org.androidtown.tino;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CheckFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button btn_add;

    private ArrayList<Model> item_list = new ArrayList<>();
    private ModelAdapter mAdapter;
    String res;
    private Parcelable state;
    Parcelable recyclerViewState;

    public static CheckFragment newInstance() {
        return new CheckFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_checklist, container, false);
        Parcelable recyclerViewState;

        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        btn_add= (Button) layout.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ModelAdapter(item_list);
        recyclerView.setAdapter(mAdapter);


        return layout;
    }


    public void show (){
        final EditText edittext = new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("할일을 입력해줘!");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        //Toast.makeText(getApplicationContext(),edittext.getText().toString(), Toast.LENGTH_LONG).show();
                        res = edittext.getText().toString();
                        item_list.add(new Model(res, false));
                        mAdapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.show();
    }

}