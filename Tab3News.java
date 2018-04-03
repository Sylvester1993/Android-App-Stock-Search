package com.example.sylvester.stockmarketsearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.content.ActivityNotFoundException;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sylvester on 26/11/2017.
 */

public class Tab3News extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_news, container, false);
        String symbol = null;
        Bundle bundle = this.getArguments();
        if(bundle != null){
            symbol = bundle.getString("symbol");

        }
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://csci571-anothernodejs-env.us-east-2.elasticbeanstalk.com/?type=news&symbol=" + symbol;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            String item1Title = response.getString("item1_title");
                            String item1Link = response.getString("item1_link");
                            String item1Author = response.getString("item1_author");
                            String item1Date = response.getString("item1_date");
                            String item2Title = response.getString("item2_title");
                            String item2Link = response.getString("item2_link");
                            String item2Author = response.getString("item2_author");
                            String item2Date = response.getString("item2_date");
                            String item3Title = response.getString("item3_title");
                            String item3Link = response.getString("item3_link");
                            String item3Author = response.getString("item3_author");
                            String item3Date = response.getString("item3_date");
                            String item4Title = response.getString("item4_title");
                            String item4Link = response.getString("item4_link");
                            String item4Author = response.getString("item4_author");
                            String item4Date = response.getString("item4_date");
                            String item5Title = response.getString("item5_title");
                            String item5Link = response.getString("item5_link");
                            String item5Author = response.getString("item5_author");
                            String item5Date = response.getString("item5_date");
                            String item6Title = response.getString("item6_title");
                            String item6Link = response.getString("item6_link");
                            String item6Author = response.getString("item6_author");
                            String item6Date = response.getString("item6_date");
                            String item7Title = response.getString("item7_title");
                            String item7Link = response.getString("item7_link");
                            String item7Author = response.getString("item7_author");
                            String item7Date = response.getString("item7_date");
                            String item8Title = response.getString("item8_title");
                            String item8Link = response.getString("item8_link");
                            String item8Author = response.getString("item8_author");
                            String item8Date = response.getString("item8_date");
                            String item9Title = response.getString("item9_title");
                            String item9Link = response.getString("item9_link");
                            String item9Author = response.getString("item9_author");
                            String item9Date = response.getString("item9_date");
                            String item10Title = response.getString("item10_title");
                            String item10Link = response.getString("item10_link");
                            String item10Author = response.getString("item10_author");
                            String item10Date = response.getString("item10_date");


                            final String [] news = new String[10];
                            news[0] = item1Title + ";" + item1Link + ";" + item1Author + ";" + item1Date;
                            news[1] = item2Title + ";" + item2Link + ";" + item2Author + ";" + item2Date;
                            news[2] = item3Title + ";" + item3Link + ";" + item3Author + ";" + item3Date;
                            news[3] = item4Title + ";" + item4Link + ";" + item4Author + ";" + item4Date;
                            news[4] = item5Title + ";" + item5Link + ";" + item5Author + ";" + item5Date;
                            news[5] = item6Title + ";" + item6Link + ";" + item6Author + ";" + item6Date;
                            news[6] = item7Title + ";" + item7Link + ";" + item7Author + ";" + item7Date;
                            news[7] = item8Title + ";" + item8Link + ";" + item8Author + ";" + item8Date;
                            news[8] = item9Title + ";" + item9Link + ";" + item9Author + ";" + item9Date;
                            news[9] = item10Title + ";" + item10Link + ";" + item10Author + ";" + item10Date;

                            ListAdapter listAdapter = new NewsAdapter(getActivity().getApplicationContext(),news);
                            ListView newsTable = (ListView)rootView.findViewById(R.id.newsTable);
                            newsTable.setAdapter(listAdapter);
                            newsTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String[] content = news[i].split(";");
                                    String linkUrl = content[1];

                                    try {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
                                        startActivity(browserIntent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(getActivity().getApplicationContext(), "No application can handle this request."
                                                + " Please install a web browser",  Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }

                                }
                            });

                            ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar2);
                            progressBar.setVisibility(View.GONE);


                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity().getApplicationContext(),"Nothing found!",Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
/*
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://csci571-anothernodejs-env.us-east-2.elasticbeanstalk.com/?type=news&symbol=" + symbol;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            /*
                            String item1Title = response.getString("item1_title");
                            String item1Link = response.getString("item1_link");
                            String item1Author = response.getString("item1_author");
                            String item1Date = response.getString("item1_date");
                            String item2Title = response.getString("item2_title");
                            String item2Link = response.getString("item2_link");
                            String item2Author = response.getString("item2_author");
                            String item2Date = response.getString("item2_date");
                            String item3Title = response.getString("item3_title");
                            String item3Link = response.getString("item3_link");
                            String item3Author = response.getString("item3_author");
                            String item3Date = response.getString("item3_date");
                            String item4Title = response.getString("item4_title");
                            String item4Link = response.getString("item4_link");
                            String item4Author = response.getString("item4_author");
                            String item4Date = response.getString("item4_date");
                            String item5Title = response.getString("item5_title");
                            String item5Link = response.getString("item5_link");
                            String item5Author = response.getString("item5_author");
                            String item5Date = response.getString("item5_date");


                            String [] news = new String[5];
                            news[0] = item1Title + ";" + item1Link + ";" + item1Author + ";" + item1Date;
                            news[1] = item2Title + ";" + item2Link + ";" + item2Author + ";" + item2Date;
                            news[2] = item3Title + ";" + item3Link + ";" + item3Author + ";" + item3Date;
                            news[3] = item4Title + ";" + item4Link + ";" + item4Author + ";" + item4Date;
                            news[4] = item5Title + ";" + item5Link + ";" + item5Author + ";" + item5Date;

                            ListAdapter listAdapter = new NewsAdapter(getActivity().getApplicationContext(),news);
                            ListView newsTable = (ListView)rootView.findViewById(R.id.newsTable);
                            newsTable.setAdapter(listAdapter);

                            ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.INVISIBLE);


                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity().getApplicationContext(),"Nothing found!",Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
*/
        return rootView;
    }

}
