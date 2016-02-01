package com.example.chanwon.appsent.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;
import com.example.chanwon.appsent.Tab.SlidingTabLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;

import java.io.IOException;
import java.util.ArrayList;

//import com.example.chanwon.appsent.Analytics.SentimentTool;


public class RankingFeatures extends ActionBarActivity {
    DatabaseHelper mydb;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_feature);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawer drawerFragment = (NavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs1);
        mTabs.setDistributeEvenly(true);

        mTabs.setViewPager(mPager);
        mydb = new DatabaseHelper(this);

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

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs1);


        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

    public static class MyFragment extends Fragment {


        private TextView textView, textView1;
        private FrameLayout graphpage, graphpage1;
        HorizontalBarChart mChart;
        Legend mLegend;
        BarChart mBarChart;
        private Button buttonTimeline;
        private String[] xData = {};
        private Integer[] yData = {};
        private Spinner spinner;
        DatabaseHelper mydb;


        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        public void showMessage(String title, String Message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(Message);
            builder.show();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_rankings, container, false);
            graphpage = (FrameLayout) layout.findViewById(R.id.graphframe);
            textView = (TextView) layout.findViewById(R.id.graphTitle);
            textView1 = (TextView) layout.findViewById(R.id.subTitle);
            buttonTimeline = (Button) layout.findViewById(R.id.buttonTimeline);
            spinner = (Spinner) layout.findViewById(R.id.spinner);
            spinner.setBackgroundColor(Color.LTGRAY);

            ArrayList<String> monthlist = new ArrayList<>();
            monthlist.add("Overall");
            monthlist.add("201401");
            monthlist.add("201401");
            monthlist.add("201402");
            monthlist.add("201403");
            monthlist.add("201404");
            monthlist.add("201405");

            ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.values, monthlist);
            spinner.setAdapter(adapter);


            mydb = new DatabaseHelper(getActivity());

            Bundle bundle = getArguments();
            if (bundle != null) {
                if (bundle.getInt("position") == 0) {
                    textView.setText("Flutter");
                    textView1.setText("Top 10 Repeated Features");
                    try {
                        graphForPositive();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // timeline();

                }
                if (bundle.getInt("position") == 1) {
                    textView.setText("Flutter");
                    textView1.setText("Top 10 Repeated Features");

                    try {
                        graphForNeutral();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //timeline();
                }
                if (bundle.getInt("position") == 2) {
                    textView.setText("Flutter");
                    textView1.setText("Top 10 Repeated Features");
                    try {
                        graphForNegative();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //GraphBoth();
                    //timeline();
                }

            }
            return layout;
        }

        public void timeline() {
            buttonTimeline.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = getArguments();
                            if (bundle.getInt("position") == 0) {
                                startActivity(new Intent(getActivity(), TimeLinePopup.class));
                            } else if (bundle.getInt("position") == 1) {
                                startActivity(new Intent(getActivity(), TimelinePopupEmo.class));
                            }
                        }
                    }
            );
        }

        public void graphForNeutral() throws IOException {
            {
                //textView.setText("This page is " + bundle.getInt("position"));
                mChart = new HorizontalBarChart(getActivity());
                mChart.setHighlightEnabled(false);
                mChart.setDrawGridBackground(false);
                graphpage.addView(mChart);
                graphpage.setBackgroundColor(Color.WHITE);
                //configure pie chart
                //set a chart value selected listnerer
                XAxis x1 = mChart.getXAxis();
                x1.setPosition(XAxis.XAxisPosition.BOTTOM);
                x1.setTextColor(Color.BLACK);
                x1.setDrawGridLines(true);
                x1.setAvoidFirstLastClipping(true);
                x1.setDrawLabels(true);
                x1.setDrawGridLines(false);
                YAxis y1 = mChart.getAxisLeft();
                y1.setEnabled(true);
                y1.setDrawLabels(true);
                y1.setTextSize(10);
                y1.setDrawGridLines(false);
                YAxis y2 = mChart.getAxisRight();
                y2.setEnabled(false);
                y2.setDrawLabels(false);
                Legend u = mChart.getLegend();
                u.setFormSize(0);
                mChart.setTouchEnabled(false);


                mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry entry, int i, Highlight highlight) {
                        //display msg when value selected
                        if (entry == null) {
                            return;

                        }
                        Toast.makeText(getActivity(), xData[entry.getXIndex()] + "=" + new Integer((int) entry.getVal()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }

                });
                ///
                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                ArrayList<String> xVals1 = new ArrayList<String>();

                int allsentences = 0;
                Cursor res1 = mydb.rankNeutralFeature();
                int indexnumber = 9;
                while (res1.moveToNext()) {
                    xVals1.add(res1.getString(0));
                    yVals1.add(new BarEntry(res1.getInt(1), indexnumber));
                    indexnumber--;
                }
                ArrayList<String> xVals2 = new ArrayList<String>();
                for(int j = yVals1.size() - 1; j>=0; j--){
                    xVals2.add(xVals1.get(j));
                }


                //create pie data set
                BarDataSet dataSet = new BarDataSet(yVals1, " ");

                //add many colors
                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);
                colors.add(ColorTemplate.getHoloBlue());
                dataSet.setColors(colors);
                //instantiate pie data object now
                BarData data = new BarData(xVals2, dataSet);
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.BLACK);
                mChart.setData(data);
                //undo all highlights
                mChart.highlightValues(null);
                //update pie chart
                mChart.invalidate();


            }
        }
        public void graphForNegative() throws IOException {
            {
                //textView.setText("This page is " + bundle.getInt("position"));
                mChart = new HorizontalBarChart(getActivity());
                mChart.setHighlightEnabled(false);
                mChart.setDrawGridBackground(false);
                graphpage.addView(mChart);
                graphpage.setBackgroundColor(Color.WHITE);
                //configure pie chart
                //set a chart value selected listnerer
                XAxis x1 = mChart.getXAxis();
                x1.setPosition(XAxis.XAxisPosition.BOTTOM);
                x1.setTextColor(Color.BLACK);
                x1.setDrawGridLines(true);
                x1.setAvoidFirstLastClipping(true);
                x1.setDrawLabels(true);
                x1.setDrawGridLines(false);
                YAxis y1 = mChart.getAxisLeft();
                y1.setEnabled(true);
                y1.setDrawLabels(true);
                y1.setTextSize(10);
                y1.setDrawGridLines(false);
                YAxis y2 = mChart.getAxisRight();
                y2.setEnabled(false);
                y2.setDrawLabels(false);
                Legend u = mChart.getLegend();
                u.setFormSize(0);
                mChart.setTouchEnabled(false);


                mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry entry, int i, Highlight highlight) {
                        //display msg when value selected
                        if (entry == null) {
                            return;

                        }
                        Toast.makeText(getActivity(), xData[entry.getXIndex()] + "=" + new Integer((int) entry.getVal()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }

                });
                ///
                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                ArrayList<String> xVals1 = new ArrayList<String>();

                int allsentences = 0;
                Cursor res1 = mydb.rankNegativeFeature();
                int indexnumber = 9;
                while (res1.moveToNext()) {
                    xVals1.add(res1.getString(0));
                    yVals1.add(new BarEntry(res1.getInt(1), indexnumber));
                    indexnumber--;
                }
                ArrayList<String> xVals2 = new ArrayList<String>();
                for(int j = yVals1.size() - 1; j>=0; j--){
                    xVals2.add(xVals1.get(j));
                }


                //create pie data set
                BarDataSet dataSet = new BarDataSet(yVals1, " ");

                //add many colors
                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);
                colors.add(ColorTemplate.getHoloBlue());
                dataSet.setColors(colors);
                //instantiate pie data object now
                BarData data = new BarData(xVals2, dataSet);
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.BLACK);
                mChart.setData(data);
                //undo all highlights
                mChart.highlightValues(null);
                //update pie chart
                mChart.invalidate();


            }
        }public void graphForPositive() throws IOException {
            {
                //textView.setText("This page is " + bundle.getInt("position"));
                mChart = new HorizontalBarChart(getActivity());
                mChart.setHighlightEnabled(false);
                mChart.setDrawGridBackground(false);
                graphpage.addView(mChart);
                graphpage.setBackgroundColor(Color.WHITE);
                //configure pie chart
                //set a chart value selected listnerer
                XAxis x1 = mChart.getXAxis();
                x1.setPosition(XAxis.XAxisPosition.BOTTOM);
                x1.setTextColor(Color.BLACK);
                x1.setDrawGridLines(true);
                x1.setAvoidFirstLastClipping(true);
                x1.setDrawLabels(true);
                x1.setDrawGridLines(false);
                YAxis y1 = mChart.getAxisLeft();
                y1.setEnabled(true);
                y1.setDrawLabels(true);
                y1.setTextSize(10);
                y1.setDrawGridLines(false);
                YAxis y2 = mChart.getAxisRight();
                y2.setEnabled(false);
                y2.setDrawLabels(false);
                Legend u = mChart.getLegend();
                u.setFormSize(0);
                mChart.setTouchEnabled(false);


                mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry entry, int i, Highlight highlight) {
                        //display msg when value selected
                        if (entry == null) {
                            return;

                        }
                        Toast.makeText(getActivity(), xData[entry.getXIndex()] + "=" + new Integer((int) entry.getVal()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }

                });
                ///
                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                ArrayList<String> xVals1 = new ArrayList<String>();

                int allsentences = 0;
                Cursor res1 = mydb.rankPositiveFeature();
                int indexnumber = 9;
                while (res1.moveToNext()) {
                    xVals1.add(res1.getString(0));
                    yVals1.add(new BarEntry(res1.getInt(1), indexnumber));
                    indexnumber--;
                }
                ArrayList<String> xVals2 = new ArrayList<String>();
                for(int j = yVals1.size() - 1; j>=0; j--){
                    xVals2.add(xVals1.get(j));
                }


                //create pie data set
                BarDataSet dataSet = new BarDataSet(yVals1, " ");

                //add many colors
                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);
                colors.add(ColorTemplate.getHoloBlue());
                dataSet.setColors(colors);
                //instantiate pie data object now
                BarData data = new BarData(xVals2, dataSet);
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.BLACK);
                mChart.setData(data);
                //undo all highlights
                mChart.highlightValues(null);
                //update pie chart
                mChart.invalidate();


            }
        }

    }

}


