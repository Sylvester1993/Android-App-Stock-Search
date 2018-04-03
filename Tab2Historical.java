package com.example.sylvester.stockmarketsearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Sylvester on 26/11/2017.
 */

public class Tab2Historical extends Fragment {

    WebView highStock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_historical, container, false);
        String symbol = null;
        Bundle bundle = this.getArguments();
        if(bundle != null){
            symbol = bundle.getString("symbol");
        }

        highStock = (WebView)rootView.findViewById(R.id.highStock);
        highStock.getSettings().setDomStorageEnabled(true);
        highStock.getSettings().setJavaScriptEnabled(true);
        highStock.loadUrl("file:///android_asset/highStock.html");
        highStock.addJavascriptInterface(new WebInterface(getActivity().getApplicationContext(), symbol), "TransportInterface");

        return rootView;
    }

}
