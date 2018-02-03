package com.example.gaurk.triplebyte_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gaurk on 2/2/2018.
 */

public class CustomListAdapter extends BaseAdapter {

    ArrayList<CatListObject> imgObjects;
    Context context;
    LayoutInflater layoutInflater;
    int size;

    public CustomListAdapter(ArrayList<CatListObject> _imgObjs, Context _context,int _size) {
        imgObjects = _imgObjs;
        context = _context;
        layoutInflater = LayoutInflater.from(context);
        size = _size;
    }

    @Override
    public int getCount() {
        return imgObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return imgObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        holder hold = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_item, null);
            hold = new holder();
            hold.time_tv = (TextView) convertView.findViewById(R.id.cattimetextView);
            hold.desc_tv = (TextView) convertView.findViewById(R.id.catdesctextView);
            hold.title_tv = (TextView) convertView.findViewById(R.id.cattitletextView);
            hold.imgDisp = (ImageView) convertView.findViewById(R.id.catimgview);

            convertView.setTag(hold);

        } else {
            hold = (holder) convertView.getTag();
        }
        hold.time_tv.setText(imgObjects.get(position).getTime());
        hold.desc_tv.setText(imgObjects.get(position).getDescription());
        hold.title_tv.setText(imgObjects.get(position).getTitle());


        Picasso.with(context)
                .load(imgObjects.get(position).getCat_url()).resize(size, size)
                .centerCrop()
                .into(hold.imgDisp);

        return convertView;
    }

    static class holder {
        TextView title_tv;
        TextView time_tv;
        TextView desc_tv;
        ImageView imgDisp;

    }
}

