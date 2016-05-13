package com.nikki.higherorlowerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    int randNum;

    public void clickMe(View view)
    {
        System.out.println("Random Number Generated "+ randNum);
        EditText myTextfield = (EditText) findViewById(R.id.userGuess);
        Log.i("Number entered by the User", myTextfield.getText().toString());
        String usersGuess = myTextfield.getText().toString();

        int userGuess = Integer.parseInt(usersGuess);
        if(userGuess > randNum)
        {
            Toast.makeText(getApplicationContext(),"Lower",Toast.LENGTH_LONG).show();
        }
        else if (userGuess < randNum)
        {
            Toast.makeText(getApplicationContext(),"Higher",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Correct! Try Again?",Toast.LENGTH_LONG).show();

    }

    public void tryAgain(View view)
    {
        Random randGen =new Random();
        randNum = randGen.nextInt(21);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Random randGen =new Random();
        randNum = randGen.nextInt(21);

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
