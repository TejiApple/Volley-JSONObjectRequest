package com.example.volleysampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private TextView txtShowTextResult;
        RequestQueue requestQueue;
        JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtShowTextResult = findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(this);
        final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyBrJ3ec9wTuS6L-xHkaXLU8BJbFsx_LZ9o";
//        final String url = "https://api.coingecko.com/api/v3/coins/list";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, (Response.Listener<org.json.JSONObject>) response -> {
            try {
                StringBuilder formattedResult = new StringBuilder();
                JSONArray responseJSONArray = response.getJSONArray("results");
                for (int i = 0; i < responseJSONArray.length(); i++) {
                    formattedResult.append("\n" + responseJSONArray.getJSONObject(i).get("name") + "=> \t" + responseJSONArray.getJSONObject(i).get("rating"));
                }
                txtShowTextResult.setText("List of Restaurants \n" + " Name" + "\t Rating \n" + formattedResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            findViewById(R.id.progressBar).setVisibility(View.GONE);
        }, error -> txtShowTextResult.setText("An Error occured while making the request"));
        requestQueue.add(jsonObjectRequest);
    }
}