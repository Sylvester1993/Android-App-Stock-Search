package com.example.sylvester.stockmarketsearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button answerGetQuote, answerClear;
    private  AutoCompleteTextView autoCompleteText;
    private RequestQueue requestQueue;
    private String[] listArray = new String[0];
    private ArrayAdapter adapter;
    SharedPreferences favor;
    String[] favoriteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerGetQuote = (Button)findViewById(R.id.get_quote_button);
        answerClear = (Button)findViewById(R.id.clear_button);
        requestQueue = Volley.newRequestQueue(this);

        autoCompleteText = (AutoCompleteTextView)findViewById(R.id.autoComplete_text);

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listArray);
        autoCompleteText.setThreshold(1);
        autoCompleteText.setAdapter(adapter);

        final TextWatcher autoCompleteWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {




            }

            @Override
            public void afterTextChanged(Editable editable) {

                String incompleteSymbol = String.valueOf(autoCompleteText.getText());
                String url = "http://newnodejs-csci571.us-east-2.elasticbeanstalk.com/?type=autocomplete&symbol=" + incompleteSymbol;
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try{

                                    listArray = new String[response.length()];
                                    for (int j = 0;j < response.length() &&  j < 5;j++) {
                                        JSONObject jsonResponse = response.getJSONObject(j);
                                        listArray[j] = jsonResponse.getString("Symbol") + "-" + jsonResponse.getString("Name") + " (" + jsonResponse.getString("Exchange") + ")";

                                    }
                                    List<String> companies = new ArrayList<>();
                                    for(String s: listArray)
                                        if(s != null)
                                            companies.add(s);

                                    adapter.clear();
                                    adapter.addAll(companies);
                                    autoCompleteText.setThreshold(1);
                                    autoCompleteText.setAdapter(adapter);

                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getApplicationContext(),"Nothing found!",Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(jsonArrayRequest);

            }
        };
        autoCompleteText.addTextChangedListener(autoCompleteWatcher);

        favor = getApplicationContext().getSharedPreferences("MyFavor", 0); // 0 - for private mode
        SharedPreferences.Editor editor = favor.edit();

        Map<String,?> favoriteMap = favor.getAll();

        favoriteArray = new String[favoriteMap.size()];
        int k = 0;

        for(String key : favoriteMap.keySet()){
            favoriteArray[k] = key + ";" + favoriteMap.get(key).toString();
            k++;
        }

        ListView favoriteList = (ListView) findViewById(R.id.favorite_list);
        //registerForContextMenu(favoriteList);
        ListAdapter favoriteAdapter = new FavoriteAdapter(getApplicationContext(),favoriteArray);
        favoriteList.setAdapter(favoriteAdapter);



    }


    public void onGetQuoteClick(View view) {

        String autocompleteContent = String.valueOf(autoCompleteText.getText());
        String[] contentArray = autocompleteContent.split("-");
        String symbol = contentArray[0];
        Toast.makeText(getApplicationContext(),symbol,Toast.LENGTH_SHORT).show();
        Intent getStockInfoIntent = new Intent(this,SecondActivity.class);
        getStockInfoIntent.putExtra("symbol",symbol);
        startActivity(getStockInfoIntent);

    }

    public void onClearClick(View view) {

        //autoCompleteText.;

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String[] favoriteList = data.getStringArrayExtra("favoriteList");
    }
    */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.favorite_list){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.deletemenu, menu);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_yes:
                SharedPreferences.Editor editor = favor.edit();
                editor.remove(favoriteArray[info.position]);
                editor.commit();
                /*
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(favoriteStockList.get(info.position).getStockName());
                editor.commit();
                findStockinList(favoriteStockList.get(info.position).getStockName());
                favoriteStockList.remove(info.position);
                favoriteAdapter.notifyDataSetChanged();
                */
                return true;
            case R.id.menu_no:
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

}
