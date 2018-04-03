package com.example.sylvester.stockmarketsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Sylvester on 27/11/2017.
 */

public class NewsAdapter extends ArrayAdapter<String> {

    public NewsAdapter(Context context, String[] values){
        super(context,R.layout.news_row_layout,values);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.news_row_layout,parent,false);
        String data = getItem(position);
        String[] content = data.split(";");
        TextView title = (TextView)theView.findViewById(R.id.newsTitle);
        title.setText(content[0]);
        TextView author = (TextView)theView.findViewById(R.id.newsAuthor);
        String authorText = "Author: " + content[2];
        author.setText(authorText);
        TextView date = (TextView)theView.findViewById(R.id.newsDate);
        String dateText = "Date: " + content[3];
        date.setText(dateText);
        return theView;
    }

}
