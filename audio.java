package com.nikki.audio_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.util.Log;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.media.AudioManager;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mpPlayer;
    AudioManager audioManager;
    public void play(View view)
    {
        mpPlayer.start();
    }
    public void pause(View view)
    {
        mpPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mpPlayer = MediaPlayer.create(this, R.raw.laugh);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);
        volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar value",Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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