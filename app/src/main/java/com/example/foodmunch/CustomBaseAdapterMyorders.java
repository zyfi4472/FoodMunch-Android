package com.example.foodmunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapterMyorders extends BaseAdapter {

    Context context;
    String listmenu[];
    int listimages[];
    LayoutInflater inflater;

    public CustomBaseAdapterMyorders(Context ctx, String [] menulist, int [] images){
        this.context = ctx;
        this.listmenu = menulist;
        this.listimages = images;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount()
    {
        return listmenu.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_myorders_list, null);
        TextView foodtext = (TextView) convertView.findViewById(R.id.t1);
        ImageView foodimage = (ImageView) convertView.findViewById(R.id.imageIcon);
        foodtext.setText(listmenu[position]);
        foodimage.setImageResource(listimages[position]);
        return convertView;
    }
}
