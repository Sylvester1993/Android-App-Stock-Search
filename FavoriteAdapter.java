package com.example.sylvester.stockmarketsearch;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sylvester on 27/11/2017.
 */

public class FavoriteAdapter extends ArrayAdapter<String> {

    public FavoriteAdapter(Context context,String[] values){
        super(context,R.layout.favorite_layout,values);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.favorite_layout,parent,false);

        String data = getItem(position);
        String[] dataArray = data.split(";");
        TextView favorite_symbol = (TextView)theView.findViewById(R.id.favorite_symbol);
        TextView favorite_price = (TextView)theView.findViewById(R.id.favorite_price);
        TextView favorite_change = (TextView)theView.findViewById(R.id.favorite_change);
        favorite_symbol.setText(dataArray[0]);
        favorite_price.setText(dataArray[1]);
        String flag = dataArray[2].substring(0,1);
        if(flag.equals("-")){
            favorite_change.setTextColor(Color.RED);
            favorite_change.setText(dataArray[2]);
        }
        else{
            favorite_change.setTextColor(Color.GREEN);
            favorite_change.setText(dataArray[2]);
        }

        return theView;
    }
}
