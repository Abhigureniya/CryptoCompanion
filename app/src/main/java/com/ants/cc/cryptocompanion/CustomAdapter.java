package com.ants.cc.cryptocompanion;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by abhijeet on 27/4/18.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titles;
    private final Integer[] resources;

    public CustomAdapter(Activity context, String[] titles, Integer[] resources){
        super(context,R.layout.custom_listview_layout, titles);
        this.context = context;
        this.titles = titles;
        this.resources = resources;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflator = context.getLayoutInflater();
        View rowView = inflator.inflate(R.layout.custom_listview_layout, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.textViewName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewImg);

        titleText.setText(titles[position]);
        imageView.setImageResource(resources[position]);

        return rowView;
    }
}
