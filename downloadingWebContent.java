package com.nikki.downloadingwebcontent;

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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
     public class DownloadTask extends AsyncTask<String, Void, String>{

         @Override
         protected String doInBackground(String... urls) {
             String result = "";
             URL url;
             HttpURLConnection urlConnection = null;

             try{
              url = new URL(urls[0]);
                 urlConnection = (HttpURLConnection) url.openConnection();
                 InputStream in = urlConnection.getInputStream();
                 InputStreamReader reader = new InputStreamReader(in);
                 int data = reader.read();
                 while(data != -1){
                     char current = (char) data;
                     result += current;
                     data = reader.read();
                 }
                 return result;
             }
             catch (Exception e) {
                 e.printStackTrace();
                 return "failed";
             }
         }
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DownloadTask task  = new DownloadTask();
        String result= null;
        try {
            result = task.execute("https://www.ecowebhosting.co.uk/").get();
        }
        catch (InterruptedException e)
        {
         e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        Log.i("Contents of URL", result);

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
