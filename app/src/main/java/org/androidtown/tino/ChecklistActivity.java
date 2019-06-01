package org.androidtown.tino;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ChecklistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btn_add;

    private ArrayList<Model> item_list = new ArrayList<>();
    private ModelAdapter mAdapter;
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        initControls();
    }

    private void initControls() {


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn_add= (Button) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ModelAdapter(item_list);
        recyclerView.setAdapter(mAdapter);



    }
    public void show (){
        final EditText edittext = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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