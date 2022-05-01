package com.munderstand.autocool;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CarsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<VoitureVilleLieuModel> carsModelArrayList;
    private TextView tvId;



    public CarsAdapter(Context context, ArrayList<VoitureVilleLieuModel> carsModelArrayList) {
        this.context = context;
        this.carsModelArrayList = carsModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
//        return 1;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return carsModelArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return carsModelArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);

            holder.tvVille = (TextView) convertView.findViewById(R.id.tvVille);
            holder.tvLieu = (TextView) convertView.findViewById(R.id.tvLieu);
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            convertView.setTag(holder);
        }else {

            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvVille.setText(carsModelArrayList.get(position).getVille());
        holder.tvLieu.setText(carsModelArrayList.get(position).getLieu());
        holder.tvId.setText(String.valueOf(carsModelArrayList.get(position).getId()));


        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /**
         * Inflating the root view and all his children and set them to a View Object.
         *
         */
        View row = layoutInflater.inflate(R.layout.list_item,null);

        // Get all the views in my row
        this.tvId = (TextView) row.findViewById(R.id.tvId);

        // Set values to all the views in my row
//        this.tvId.setText(convertView.getA());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Test", "cliekc 2");

//                Toast.makeText(parent.getContext(), "Selected: " + view.getId(), Toast.LENGTH_LONG).show();

            }
        });



        return convertView;
    }

    private class ViewHolder {
        protected TextView tvVille, tvLieu,  tvId;
    }
}
