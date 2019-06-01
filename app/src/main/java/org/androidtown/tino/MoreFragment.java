package org.androidtown.tino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MoreFragment extends Fragment {
    ArrayList<String> menus = new ArrayList<String>();
    public static MoreFragment newInstance() {
        return new MoreFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_more, container, false);

        menus.add("개발자 정보");
        menus.add("About TINO");
        menus.add("문의하기");

        ListView listView = (ListView) layout.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menus);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(listener);
        return layout;
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(menus.get(position)== "개발자 정보" ){
                Intent intent = new Intent(getActivity(), DeveloperInfoActivity.class);
                startActivity(intent);
            }
            if(menus.get(position)== "About TINO" ){
                Intent intent = new Intent(getActivity(), AboutTinoActivity.class);
                startActivity(intent);
            }
            if(menus.get(position)== "문의하기" ){
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                // email setting 배열로 해놔서 복수 발송 가능
                String[] address = {"espero90@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT,"[TINO관련 문의하기]");
                email.putExtra(Intent.EXTRA_TEXT,".\n");
                startActivity(email);

            }
        }
    };
}