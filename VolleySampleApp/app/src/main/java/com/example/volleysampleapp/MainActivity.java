package com.example.volleysampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtShowTextResult;
        RequestQueue requestQueue;
//        JsonObjectRequest jsonObjectRequest;
        JsonArrayRequest jsonArrayRequest;
        ArrayList<Coin> mData = new ArrayList<>();

        ListView listView;
        ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtShowTextResult = findViewById(R.id.textView);
        listView = findViewById(R.id.listview);

        requestQueue = Volley.newRequestQueue(this);
//        final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyBrJ3ec9wTuS6L-xHkaXLU8BJbFsx_LZ9o";
        final String url = "https://api.coingecko.com/api/v3/coins/list";

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, (Response.Listener<org.json.JSONArray>) response -> {
            try {
                StringBuilder formattedResult = new StringBuilder();

                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String symbol = jsonObject.getString("symbol");
                    String name = jsonObject.getString("name");

                    mData.add(new Coin(id, symbol, name));
                }
                arrayAdapter = new ArrayAdapter<>(this, R.layout.listview, R.id.tvList, mData);
                listView.setAdapter(arrayAdapter);
//                Log.v("Aporo",mData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            findViewById(R.id.progressBar).setVisibility(View.GONE);
        }, error -> txtShowTextResult.setText("An Error occured while making the request"));
        requestQueue.add(jsonArrayRequest);
    }
}

class Coin{
    String id;
    String symbol;
    String name;

    public Coin(String id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }
}