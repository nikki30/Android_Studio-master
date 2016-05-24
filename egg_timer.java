package com.nikki.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    CountDownTimer countDownTimer;
    Boolean isActive = false;

    public void resetTimer ()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("GO");
        timerSeekBar.setEnabled(true);
        isActive = false;
    }

    public void updateTimer(int secondsLeft)
    {
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;
        String secondString = Integer.toString(seconds);

        if(seconds <= 9)
        {
            secondString = "0"  + secondString;
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }

    public void controlTimer(View view)
    {
        if(isActive == false) {
            isActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("STOP");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                }
            }.start();
        }
            else
            {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
         timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        controllerButton.setText("STOP");
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
