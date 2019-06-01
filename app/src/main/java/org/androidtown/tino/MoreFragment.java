package org.androidtown.tino;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MoreFragment extends Fragment {

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_more, container, false);

        String[] menus = {"Send Message", "Rate Alerts", "Currency Profile", "Help"};
        ListView listView = (ListView) layout.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menus);
        listView.setAdapter(adapter);

        return layout;
    }

}