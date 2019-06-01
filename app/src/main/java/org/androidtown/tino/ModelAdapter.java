package org.androidtown.tino;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;



public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {


    public ArrayList<Model> item_list;


    public ModelAdapter(ArrayList<Model> arrayList) {

        item_list = arrayList;
    }

    @Override
    public ModelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ModelAdapter.ViewHolder holder, int position) {

        final int pos = position;

        holder.item_name.setText(item_list.get(position).getItemName());

        holder.chkSelected.setChecked(item_list.get(position).isSelected());

        holder.chkSelected.setTag(item_list.get(position));

        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Model model = (Model) cb.getTag();

                model.setSelected(cb.isChecked());
                item_list.get(pos).setSelected(cb.isChecked());

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                deleteItemFromList(v, pos);


            }
        });

    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }


    // confirmation dialog box to delete an unit
    private void deleteItemFromList(View v, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        //builder.setTitle("Dlete ");
        builder.setMessage("Delete Item ?")
                .setCancelable(false)
                .setPositiveButton("CONFIRM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                item_list.remove(position);
                                notifyDataSetChanged();


                            }
                        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });

        builder.show();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name;
        public ImageButton btn_delete;
        public CheckBox chkSelected;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            item_name = (TextView) itemLayoutView.findViewById(R.id.txt_Name);
            btn_delete = (ImageButton) itemLayoutView.findViewById(R.id.btn_delete_unit);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chk_selected);

        }
    }
}