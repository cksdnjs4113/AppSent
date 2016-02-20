package com.example.chanwon.appsent.Activity.timeline.emotion.network;

import android.app.AlertDialog;
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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;
import com.example.chanwon.appsent.Tab.SlidingTabLayout;
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


public class NetworkDisgust extends ActionBarActivity {
    DatabaseHelper mydb;
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_pos);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        getWindow().setLayout((int) (width * .9), (int) (height * .75));

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

    public static class MyFragment extends Fragment {


        HorizontalBarChart mChart;
        DatabaseHelper mydb;
        private TextView textView, textView2, textView1;
        private FrameLayout graphpage, graphpage1;
        private String[] xData = {};
        private Float[] yData = {};
        private Spinner spinner;

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
            View layout = inflater.inflate(R.layout.fragment_networks, container, false);
            graphpage = (FrameLayout) layout.findViewById(R.id.graphframe);
            spinner = (Spinner) layout.findViewById(R.id.spinner);
            spinner.setBackgroundColor(Color.LTGRAY);
            mydb = new DatabaseHelper(getActivity());

            final ArrayList<String> monthlist = new ArrayList<>();
//            monthlist.add("Overall");
//            monthlist.add("Last 4 Months");
            Cursor positivelistedlist = mydb.getlistedList("disgustfeaturetable");
            while (positivelistedlist.moveToNext()) {
                monthlist.add(positivelistedlist.getString(0));
            }
            monthlist.remove(monthlist.size() - 1);
            final Bundle bundle1 = getArguments();
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.values, monthlist);
            spinner.setAdapter(adapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle1 = getArguments();
                    if (bundle1 != null) {
                        if (bundle1.getInt("position") == 0) {
                            graphpage.removeAllViews();
                            String monthName = monthlist.get(position);
                            try {
                                graphForIssues(monthName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // timeline();

                        } else if (bundle1.getInt("position") == 1) {
                            graphpage.removeAllViews();
                            String monthName = monthlist.get(position);
                            try {
                                graphForFeature(monthName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // timeline();

                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Bundle bundle1 = getArguments();
                    if (bundle1 != null) {
                        if (bundle1.getInt("position") == 0) {
                            graphpage.removeAllViews();
                            String monthName = monthlist.get(0);
                            try {
                                graphForIssues(monthName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // timeline();

                        } else if (bundle1.getInt("position") == 1) {
                            graphpage.removeAllViews();
                            String monthName = monthlist.get(0);
                            try {
                                graphForFeature(monthName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // timeline();

                        }
                    }

                }
            });
            return layout;
        }

        public void graphForFeature(String noun) throws IOException {
            {


                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                ArrayList<String> xVals1 = new ArrayList<String>();
                ArrayList<String> xVals2 = new ArrayList<String>();

                yVals1 = new ArrayList<BarEntry>();
                xVals1 = new ArrayList<String>();

                int allsentences = 0;
                Cursor res1;
                Cursor positivelistedlist = mydb.getlistedList("disgustfeaturetable");
                ArrayList<String> rankedList = new ArrayList<>();
                while (positivelistedlist.moveToNext()) {
                    rankedList.add(positivelistedlist.getString(0));
                }
                if (rankedList.get(rankedList.size() - 1).equals("Overall")) {
                    res1 = mydb.countNounsforCoDisgust(noun, "Overall");
                } else {
                    res1 = mydb.countNounsforCoDisgust(noun, "hi");
                }

                ///

                //
                //
                //
                //
                int indexnumber = 9;
                while (res1.moveToNext()) {
                    xVals1.add(res1.getString(0));
                    yVals1.add(new BarEntry(res1.getInt(1), indexnumber));
                    indexnumber--;
                }
                xVals2 = new ArrayList<String>();
                for (int j = yVals1.size() - 1; j >= 0; j--) {
                    xVals2.add(xVals1.get(j));
                }

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
                mChart.setDescription("");


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

        public void graphForIssues(String noun) throws IOException {
            {


                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                ArrayList<String> xVals1 = new ArrayList<String>();
                ArrayList<String> xVals2 = new ArrayList<String>();

                yVals1 = new ArrayList<BarEntry>();
                xVals1 = new ArrayList<String>();

                int allsentences = 0;
                Cursor res1;
                Cursor positivelistedlist = mydb.getlistedList("disgustfeaturetable");
                ArrayList<String> rankedList = new ArrayList<>();
                while (positivelistedlist.moveToNext()) {
                    rankedList.add(positivelistedlist.getString(0));
                }
                if (rankedList.get(rankedList.size() - 1).equals("Overall")) {
                    res1 = mydb.countVerbsforCoDisgust(noun, "Overall");
                } else {
                    res1 = mydb.countVerbsforCoDisgust(noun, "hi");
                }

                //
                int indexnumber = 9;
                while (res1.moveToNext()) {
                    xVals1.add(res1.getString(0));
                    yVals1.add(new BarEntry(res1.getInt(1), indexnumber));
                    indexnumber--;
                }
                xVals2 = new ArrayList<String>();
                for (int j = yVals1.size() - 1; j >= 0; j--) {
                    xVals2.add(xVals1.get(j));
                }

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
                mChart.setDescription("");


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

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs3);

        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}


