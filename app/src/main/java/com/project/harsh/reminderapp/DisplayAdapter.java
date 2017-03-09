package com.project.harsh.reminderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by harsh on 9/3/17.
 */

class DisplayAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> id;
    private ArrayList<String> name;
    private ArrayList<String> datetime;

    public DisplayAdapter(Context mContext, ArrayList<String> id,
                          ArrayList<String> name, ArrayList<String> datetime) {
        this.mContext = mContext;
        this.id = id;
        this.name = name;
        this.datetime = datetime;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int position) {
        return id.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (convertView ==null){
            layoutInflater = (LayoutInflater)mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listcell, null);
            mHolder = new Holder();
            mHolder.id = (TextView)convertView.findViewById(R.id.txt_id);
            mHolder.name = (TextView)convertView.findViewById(R.id.txt_name);
            mHolder.datetime = (TextView)convertView.findViewById(R.id.txt_datetime);
            convertView.setTag(mHolder);
        }else {
            mHolder = (Holder)convertView.getTag();
        }
        mHolder.id.setText(id.get(position));
        mHolder.name.setText(name.get(position));
        mHolder.datetime.setText(datetime.get(position));
        return convertView;
    }

    private class Holder {
        TextView id;
        TextView name;
        TextView datetime;
    }
}
