package com.nikki.numbersandshapesapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickMe(View view)
    {
        EditText myTextField = (EditText) findViewById(R.id.userNumber);
        //System.out.println(myTextField.getText().toString());
        String userNum  = myTextField.getText().toString();
        int userNumInt = Integer.parseInt(userNum);
        System.out.println(userNumInt);

        String message = " ";

        int flag = 0;
        int flag2=0;
        int x=0;
        int z=0;
        for(int y=1;y<=100;y++)
        {
            x=x+y;
            z=y*y;
            if(x == userNumInt)
            {
                flag=1;
            }
            else
            if(z == userNumInt)
            {
                flag2=1;
            }
        }
        if ((flag==1 && flag2==1)||(userNumInt==0)||(userNumInt==1))
        {
            System.out.println("Both");
            message =  "Both";
        }
        else
        if (flag==0 && flag2==1)
        {
            System.out.println("Square");
            message =  "Square";
        }
        else if (flag==1 && flag2==0)
        {
            System.out.println("Triangular");
            message =  "Triangular";
        }
        else
        if (flag==0 && flag2==0)
        {
            System.out.println("Neither");
            message =  "Neither";
        }


        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
