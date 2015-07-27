package com.example.chanwon.appsent.Activity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

//import com.example.chanwon.appsent.Analytics.SentimentTool;
import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.Holder.AnswerTable;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;
import com.example.chanwon.appsent.Tab.SlidingTabLayout;
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.github.mikephil.charting.utils.Highlight;
//import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;


public class OverviewSentiment extends ActionBarActivity {
    DatabaseHelper mydb;
  //  private PieChart mChart;
    Button btnViewAll;
    Button btnAnalyze;
    FrameLayout subLayout;
    private String[] xData = {"Positive", "Neutral", "Negative"};
    private Float[] yData = {};
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_sentiment);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawer drawerFragment = (NavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        //mPager = (ViewPager) findViewById(R.id.Pager);

        mydb = new DatabaseHelper(this);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        btnAnalyze = (Button) findViewById(R.id.btnAnalyze);
        viewAll();
       // showGraph();



    }

//    private void showGraph() {
//        btnAnalyze.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        subLayout = (FrameLayout) findViewById(R.id.subFrame);
//                        mChart = new PieChart(getApplicationContext());
//                        //add pie chart to main layout
//                        subLayout.addView(mChart);
//                        subLayout.setBackgroundColor(Color.WHITE);
//
//                        //configure pie chart
//                        mChart.setUsePercentValues(true);
//
//                        //enable hole and conigure
//
//                        mChart.setDrawHoleEnabled(true);
//                        mChart.setHoleColorTransparent(true);
//                        mChart.setHoleRadius(40);
//                        mChart.setTransparentCircleRadius(30);
//                        mChart.setCenterText("OVERALL \n SENTIMENT");
//
//
//                        //enable rotation of the chart by touch
//                        mChart.setRotationAngle(0);
//                        mChart.setRotationEnabled(true);
//
//                        //set a chart value selected listnerer
//                        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//                            @Override
//                            public void onValueSelected(Entry entry, int i, Highlight highlight) {
//                                //display msg when value selected
//                                if (entry == null) {
//                                    return;
//
//                                }
//                                Toast.makeText(OverviewSentiment.this, xData[entry.getXIndex()] + "=" + entry.getVal(), Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNothingSelected() {
//
//                            }
//                        });
//                        addData();
//
//                        //customize Legends
//                        Legend legit = mChart.getLegend();
//                        legit.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//                        legit.setXEntrySpace(7);
//                        legit.setYEntrySpace(5);
//
//
//                    }
//                }
//        );
//    }
//
//    private void addData() {
//        ArrayList array = new ArrayList();
//        Toast.makeText(OverviewSentiment.this, "SENTIMENT", Toast.LENGTH_LONG).show();
//        Cursor res1 = mydb.getAllData();
//        if (res1.getCount() == 0) {
//            //show message
//            showMessage("Error", "Nothing saved");
//            return;
//        }
//        while (res1.moveToNext()) {
//            array.add(res1.getString(1));
//        }
//        String[] bob = new String[]{"-file"};
//        SentimentTool hi = new SentimentTool();
//        AnswerTable answer = hi.getResult(bob, array);
//
//
//        String postiveNumber = answer.getPostive();
//        String neutralNumber = answer.getNeutral();
//        String negativeNumber = answer.getNegative();
//
//        Float positiveNumber1 = Float.parseFloat(postiveNumber);
//        Float neutralNumber1 = Float.parseFloat(neutralNumber);
//        Float negativeNumber1 = Float.parseFloat(negativeNumber);
//
//        yData = new Float[]{positiveNumber1, neutralNumber1, negativeNumber1};
//
//
//        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//        for (int i = 0; i < yData.length; i++)
//            yVals1.add(new Entry(yData[i], i));
//        ArrayList<String> xVals = new ArrayList<String>();
//
//        for (int i = 0; i < xData.length; i++)
//            xVals.add(xData[i]);
//
//        //create pie data set
//
//        PieDataSet dataSet = new PieDataSet(yVals1, " ");
//        dataSet.setSliceSpace(3);
//        dataSet.setSelectionShift(5);
//
//        //add many colors
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//        dataSet.setColors(colors);
//
//        //instantiate pie data object now
//
//        PieData data = new PieData(xVals, dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.GRAY);
//
//        mChart.setData(data);
//
//        //undo all highlights
//        mChart.highlightValues(null);
//
//        //update pie chart
//        mChart.invalidate();
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
