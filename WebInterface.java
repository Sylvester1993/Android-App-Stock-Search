package com.example.sylvester.stockmarketsearch;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by Sylvester on 29/11/2017.
 */

public class WebInterface {
    Context mContext;
    String symbol;
    String chartUrl;

    /** Instantiate the interface and set the context */
    public WebInterface(Context c, String symbol) {
        this.mContext = c;
        this.symbol = symbol;
    }


    @JavascriptInterface
    public String getSymbol(){
        return symbol;
    }

}