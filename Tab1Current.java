package com.example.sylvester.stockmarketsearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.webkit.ValueCallback;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Map;


/**
 * Created by Sylvester on 26/11/2017.
 */

public class Tab1Current extends Fragment {

    WebView highChart;
    Button changeButton;
    String indicator = "Price";
    String exportUrl;
    boolean isFavorite = false;
    String[] stock_details;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_current, container, false);
        String symbol = null;
        Bundle bundle = this.getArguments();
        if(bundle != null){
            symbol = bundle.getString("symbol");
        }
        final ImageButton favorite = (ImageButton)rootView.findViewById(R.id.favorite_button);
        SharedPreferences favor = getActivity().getApplicationContext().getSharedPreferences("MyFavor", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = favor.edit();
        Map<String,?> favoriteMap= favor.getAll();
        for(String key:favoriteMap.keySet()){
            if(key.equals(symbol)){
                favorite.setImageResource(R.drawable.filled);
                isFavorite = true;
            }
        }

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

                Toast.makeText(getActivity().getApplicationContext(),"Post succeed!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {

                Toast.makeText(getActivity().getApplicationContext(),"Not post!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getActivity().getApplicationContext(),"Post error!",Toast.LENGTH_SHORT).show();

            }
        });



        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://newnodejs-csci571.us-east-2.elasticbeanstalk.com/?type=TIME_SERIES_DAILY&symbol=" + symbol;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            String stock = response.getString("symbol");
                            String lastPrice = response.getString("lastPrice");
                            String change = response.getString("change");
                            String changePercent = response.getString("changePercent");
                            String open = response.getString("open");
                            String high = response.getString("high");
                            String low = response.getString("low");
                            String volume = response.getString("volume");
                            String timeStamp = response.getString("timeStamp");

                            stock_details = new String[8];
                            stock_details[0] = stock;
                            stock_details[1] = lastPrice;
                            stock_details[2] = change + "(" + changePercent + "%)";
                            stock_details[3] = timeStamp;
                            stock_details[4] = open;
                            stock_details[5] = lastPrice;
                            stock_details[6] = low + " - " + high;
                            stock_details[7] = volume;

                            ListAdapter listAdapter = new StockAdapter(getActivity().getApplicationContext(),stock_details);
                            ListView stockDetail = (ListView)rootView.findViewById(R.id.stockDetail);
                            stockDetail.setAdapter(listAdapter);

                            ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.GONE);
                            LinearLayout indicatorBar = (LinearLayout)rootView.findViewById(R.id.indicatorBar);
                            indicatorBar.setVisibility(View.VISIBLE);

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






        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFavorite){
                    favorite.setImageResource(R.drawable.filled);
                    isFavorite = true;
                    editor.putString(stock_details[0],stock_details[1]+";"+stock_details[2]);
                    editor.commit(); // commit changes
                }
                else {
                    favorite.setImageResource(R.drawable.empty);
                    isFavorite = false;
                    editor.remove(stock_details[0]);
                    editor.commit(); // commit changes
                }
            }
        });

        Spinner indicatorSpinner = (Spinner)rootView.findViewById(R.id.indicatorSpinner);
        ArrayAdapter<String> indicatorAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.indicators));
        indicatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        indicatorSpinner.setAdapter(indicatorAdapter);
        indicatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                indicator = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageButton facebook = (ImageButton) rootView.findViewById(R.id.facebook_share_button);
        facebook.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                getUrl();
            }
        });

        highChart = (WebView)rootView.findViewById(R.id.highChart);
        highChart.getSettings().setDomStorageEnabled(true);
        highChart.getSettings().setJavaScriptEnabled(true);
        highChart.loadUrl("file:///android_asset/highChart.html");
        highChart.addJavascriptInterface(new WebInterface(getActivity().getApplicationContext(), symbol), "TransportInterface");

        changeButton = (Button)rootView.findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateHighChart();
            }
        });

        return rootView;
    }



    @JavascriptInterface
    public void activateHighChart(){
        final String webUrl = "javascript:change('"+indicator+"')";
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("Run","is used!");
                highChart.loadUrl(webUrl);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getUrl(){
        final String webUrl = "javascript:getUrl()";
        highChart.evaluateJavascript(webUrl, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d("url:", value);
                exportUrl = value;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(ShareDialog.canShow(ShareLinkContent.class)){

                            String finalUrl = exportUrl.substring(1,exportUrl.length()-1);

                            Log.d("shareurl",finalUrl);
                            ShareLinkContent content = new ShareLinkContent.Builder()
                                    .setContentUrl(Uri.parse(finalUrl))
                                    .build();
                            shareDialog.show(content);
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
