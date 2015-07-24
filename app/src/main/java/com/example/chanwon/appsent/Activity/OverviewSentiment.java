package com.example.chanwon.appsent.Activity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.chanwon.appsent.DAO.DatabaseHelper;

import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;

import java.util.ArrayList;


public class OverviewSentiment extends ActionBarActivity {
    DatabaseHelper mydb;
    Button btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_sentiment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mydb = new DatabaseHelper(this);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);

        viewAll();
        //SentimentData();


        NavigationDrawer drawerFragment = (NavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

//    public void SentimentData() {
//        ArrayList array = new ArrayList();
//        Cursor res1 = mydb.getAllData();
//        if (res1.getCount() == 0) {
//            //show message
//            showMessage("Error", "Nothing saved");
//            return;
//        }
//        while (res1.moveToNext()) {
//            array.add(res1.getString(1));
//        }
//        StringBuffer buffer = new StringBuffer();
//        String[] bob = new String[]{"-file"};
//        SentimentTool hi = new SentimentTool();
//        AnswerTable answer = hi.getResult(bob, array);
//        //String answer = hi.getResult(bob, res.getString(1));
//        buffer.append("Neutral :" + answer.getNeutral() + "\n");
//        buffer.append("Positive :" + answer.getPostive() + "\n");
//        buffer.append("Negative :" + answer.getNegative() + "\n\n");
//
//        showMessage("Data", buffer.toString());
//
//        return;
//
//
//    }


    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Cursor res = mydb.getAllData();
                        if (res.getCount() == 0) {
                            //show message
                            showMessage("Error", "Nothing saved");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Sentences :" + res.getString(1) + "\n\n");

                        }

                        //show all data
                        showMessage("Data", buffer.toString());


                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview_sentiment, menu);
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
