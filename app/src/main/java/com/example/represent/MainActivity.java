package com.example.represent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // put your API key here
    String API_KEY = "YOUR_API_KEY_HERE";
    String CIVIC_URL = "https://www.googleapis.com/civicinfo/v2/representatives";
    String GEO_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editAddress = (EditText) findViewById(R.id.address_input);
        Button btnCoordinate = (Button) findViewById(R.id.current_location);

        btnCoordinate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new GetCoordinates.exe
            }
        });

    }

    private class GetCoordinates extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = GEO_URL + String.format("?address=%s", address) + "&key=" + API_KEY;
                response = http.getHTTPData(url);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}