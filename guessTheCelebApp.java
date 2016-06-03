package com.nikki.guesscelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebURLS = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    String[] answers = new String[4];
    int locationOfCorrectAnswer =0;
    int chosenCeleb =0;
    ImageView downloadedImage;
    Button button,button2,button3,button4;

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            HttpURLConnection connection = null;
            URL url;
            try{
                url = new URL(urls[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            URL url;
            String result = " ";
            try{
                url = new URL(urls[0]);
                connection = (HttpURLConnection)url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char)data ;
                    result += current;
                    data = reader.read();
                }
                return result;

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void onClick(View view) {
        if((view.getTag().toString()).equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong! It was "+ celebNames.get(chosenCeleb),Toast.LENGTH_LONG).show();
        }
        createNewQuestion();
    }
    
    public void createNewQuestion() {
        Random rand = new Random();
        chosenCeleb = rand.nextInt(celebURLS.size());
        DownloadImage celebTask = new DownloadImage();
        Bitmap celebImage;
        try {
            celebImage = celebTask.execute(celebURLS.get(chosenCeleb)).get();
            downloadedImage.setImageBitmap(celebImage);

            locationOfCorrectAnswer = rand.nextInt(4);

            int incorrectAnswerLocation;
            for (int i = 0; i < 4; i++) {

                if (i == locationOfCorrectAnswer) {

                    answers[i] = celebNames.get(chosenCeleb);

                } else {
                    incorrectAnswerLocation = rand.nextInt(celebURLS.size());

                    while (incorrectAnswerLocation == chosenCeleb) {

                        incorrectAnswerLocation = rand.nextInt(celebURLS.size());

                    }

                    answers[i] = celebNames.get(incorrectAnswerLocation);

                }

            }

            button.setText(answers[0]);
            button2.setText(answers[1]);
            button3.setText(answers[2]);
            button4.setText(answers[3]);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int locationOfCorrectAnswer;
        downloadedImage = (ImageView)findViewById(R.id.myImage);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            Pattern p;
            Matcher m;
            result = task.execute("http://www.posh24.com/celebrities").get();
            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            p = Pattern.compile("<img src=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebURLS.add(m.group(1));
                //Log.i("Contents of URL", m.group(1));
            }
            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
                //Log.i("Contents of URL", m.group(1));
            }
            createNewQuestion();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
