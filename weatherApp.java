package com.nikki.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    String createUrl;
    EditText cityNameText;
    String cityName;
    TextView finalText;

    public void onClick(View view)
    {
        DownloadTask task = new DownloadTask();
        try {
            cityName = URLEncoder.encode(cityNameText.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(getApplicationContext(),"Couldn't find weather!",Toast.LENGTH_LONG).show();

        }
        createUrl = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=33b49c4258043c3bb9f6451d2edd0a32";
        task.execute(createUrl);
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            String result = " ";
            HttpURLConnection urlConnection = null;
            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Couldn't find weather!",Toast.LENGTH_LONG).show();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String desc="";
            String main="";
            String message = "";
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                     main = jsonPart.getString("main");
                     desc = jsonPart.getString("description");
                }
                message = "Mainly "+main+ " ( "+desc+" )";
               if(message != "") {
                   finalText.setText(message);
               }else {
                   Toast.makeText(getApplicationContext(),"Couldn't find weather!",Toast.LENGTH_LONG).show();
               }

            }
            catch (JSONException e){
                Toast.makeText(getApplicationContext(),"Couldn't find weather!",Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityNameText= (EditText) findViewById(R.id.cityName);
        finalText = (TextView)findViewById(R.id.finalText);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
