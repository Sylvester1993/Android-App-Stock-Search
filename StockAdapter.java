package com.example.sylvester.stockmarketsearch;

import android.content.Context;
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

public class StockAdapter extends ArrayAdapter<String> {

    private String[] tableHeadArray = new String[] {"Stock Symbol","Last Price","Change","Timestamp","Open","Close","Day's Range","Value"};

    public StockAdapter(Context context,String[] values){
        super(context,R.layout.row_layout,values);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout,parent,false);

        TextView tableHead = (TextView)theView.findViewById(R.id.tableHead);
        String head = tableHeadArray[position];
        tableHead.setText(head);
        String data = getItem(position);
        TextView tableData = (TextView)theView.findViewById(R.id.tableData);
        tableData.setText(data);
        if(position == 2){
            String flag = data.substring(0,1);
            if(flag.equals("-")){
                ImageView image = (ImageView)theView.findViewById(R.id.tableImage);
                image.setImageResource(R.drawable.down);
            }
            else{
                ImageView image = (ImageView)theView.findViewById(R.id.tableImage);
                image.setImageResource(R.drawable.up);
            }
        }


        return theView;
    }
}
