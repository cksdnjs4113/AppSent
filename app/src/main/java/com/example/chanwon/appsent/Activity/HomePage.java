package com.example.chanwon.appsent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.chanwon.appsent.Analytics.SentimentTool;
import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;
import com.example.chanwon.appsent.emotion.util.PropertiesManager;
import com.example.chanwon.appsent.emotion.util.TestData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class HomePage extends ActionBarActivity {
    Button btnAnal;

    //DATABASE
    DatabaseHelper mydb;
    //DATABASE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //DATABASE
        mydb = new DatabaseHelper(this);
        //DATABASE

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAnal = (Button) findViewById(R.id.btnAnal);
        analyzing();


        NavigationDrawer drawerFragment = (NavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    public void analyzing() {
        btnAnal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TestData hi1 = new TestData();
                        ArrayList testing = hi1.getchanwonAffectWords();

                        SentimentTool hi = new SentimentTool();
                        try {
                            SentimentTool.getResult(testing, getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(HomePage.this, OverviewSentiment.class));

                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_page, menu);
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
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            NavigationDrawer drawerFragment = (NavigationDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        }


        return super.onOptionsItemSelected(item);
    }

}