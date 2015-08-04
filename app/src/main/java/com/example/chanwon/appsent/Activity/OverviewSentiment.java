package com.example.chanwon.appsent.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;
import com.example.chanwon.appsent.Tab.SlidingTabLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//import com.example.chanwon.appsent.Analytics.SentimentTool;


public class OverviewSentiment extends ActionBarActivity {
    DatabaseHelper mydb;
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

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
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
            tabs = getResources().getStringArray(R.array.tabs);

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


        private TextView textView;
        private FrameLayout graphpage, graphpage1;
        PieChart mChart;
        Legend mLegend;
        BarChart mBarChart;
        private Button buttonTimeline;
        private String[] xData = {};
        private Float[] yData = {};
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
            View layout = inflater.inflate(R.layout.fragment_overviewsent, container, false);
            graphpage = (FrameLayout) layout.findViewById(R.id.graphframe);
            textView = (TextView) layout.findViewById(R.id.graphTitle);
            buttonTimeline = (Button) layout.findViewById(R.id.buttonTimeline);


            mydb = new DatabaseHelper(getActivity());

            Bundle bundle = getArguments();
            if (bundle != null) {
                if (bundle.getInt("position") == 0) {
                    textView.setText("SENTIMENT");
                    graphMethod();
                    timeline();

                }
                if (bundle.getInt("position") == 1) {
                    try {
                        textView.setText("EMOTION");
                        graphMethodForEmotion();
                        timeline();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bundle.getInt("position") == 2) {
                    textView.setText("SENTIMENT & EMOTION");
                    GraphBoth();
                    timeline();
                }

            }
            return layout;
        }

        public void timeline() {
            buttonTimeline.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), TimeLinePopup.class));

                        }
                    }
            );
        }

        public void graphMethodForEmotion() throws IOException {
            {
                //textView.setText("This page is " + bundle.getInt("position"));
                mChart = new PieChart(getActivity());
                graphpage.addView(mChart);
                graphpage.setBackgroundColor(Color.WHITE);
                //configure pie chart
                mChart.setUsePercentValues(true);
                //enable hole and conigure
                mChart.setDrawHoleEnabled(true);
                mChart.setHoleRadius(50);
                mChart.setHoleColorTransparent(true);
                mChart.setTransparentCircleRadius(60);
                mChart.setTransparentCircleColor(Color.WHITE);
                //enable rotation of the chart by touch
                mChart.setRotationAngle(0);
                mChart.setRotationEnabled(false);
                //set a chart value selected listnerer
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


                Float counthappy = 0f;
                Float countsad = 0f;
                Float countanger = 0f;
                Float countfear = 0f;
                Float countdisgust = 0f;
                Float countsurprise = 0f;
                Float countnoemo = 0f;
                ArrayList array = new ArrayList();
                ArrayList array1 = new ArrayList();
                Cursor res1 = mydb.countEmotion();
                while (res1.moveToNext()) {
                    if (res1.getString(0).equals("happy")) {
                        counthappy = Float.parseFloat(res1.getString(1));
                    } else if (res1.getString(0).equals("sad")) {
                        countsad = Float.parseFloat(res1.getString(1));
                    } else if (res1.getString(0).equals("anger")) {
                        countanger = Float.parseFloat(res1.getString(1));
                    } else if (res1.getString(0).equals("fear")) {
                        countfear = Float.parseFloat(res1.getString(1));
                    } else if (res1.getString(0).equals("disgust")) {
                        countdisgust = Float.parseFloat(res1.getString(1));
                    } else if (res1.getString(0).equals("surprise")) {
                        countsurprise = Float.parseFloat(res1.getString(1));
                    } else if (res1.getString(0).equals("noemo")) {
                        countnoemo = Float.parseFloat(res1.getString(1));
                    }
                }


                xData = new String[]{"Happy", "Sad", "Anger", "Fear", "Disgust", "Surprise", "No Emotion"};
                yData = new Float[]{counthappy, countsad, countanger, countfear, countdisgust, countsurprise, countnoemo};
                mChart.setCenterText(new Integer((int) (counthappy + countsad + countanger+ countfear+ countdisgust+ countsurprise+
                        countnoemo)) + "\nReviews");
                mChart.setCenterTextColor(ColorTemplate.getHoloBlue());
                mChart.setCenterTextSize(23);
                mChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
                mChart.setDescription("");

                ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                for (int i = 0; i < yData.length; i++)
                    yVals1.add(new Entry(yData[i], i));
                ArrayList<String> xVals = new ArrayList<String>();
                for (int i = 0; i < xData.length; i++)
                    xVals.add(xData[i]);
                //create pie data set
                PieDataSet dataSet = new PieDataSet(yVals1, " ");
                dataSet.setSliceSpace(3);
                dataSet.setSelectionShift(5);
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
                PieData data = new PieData(xVals, dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.BLACK);
                mChart.setData(data);
                //undo all highlights
                mChart.highlightValues(null);
                //update pie chart
                mChart.invalidate();
                //customize Legends
                Legend legit = mChart.getLegend();
                legit.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                legit.setXEntrySpace(3);
                legit.setYEntrySpace(2);
            }
        }

        public void graphMethod() {
            {
                //textView.setText("This page is " + bundle.getInt("position"));
                mChart = new PieChart(getActivity());
                graphpage.addView(mChart);
                graphpage.setBackgroundColor(Color.WHITE);

                //configure pie chart
                mChart.setUsePercentValues(true);

                //enable hole and conigure


                //enable rotation of the chart by touch
                mChart.setRotationAngle(0);
                mChart.setRotationEnabled(false);

                //set a chart value selected listnerer
                mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry entry, int i, Highlight highlight) {
                        //display msg when value selected
                        if (entry == null) {
                            return;

                        }
                        Toast.makeText(getActivity(), xData[entry.getXIndex()] + "=" +new Integer((int) entry.getVal()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }

                });
                ///

                Float countpos = 0f;
                Float countneu = 0f;
                Float countneg = 0f;
                Cursor res1 = mydb.countSentiment();
                while (res1.moveToNext()) {
                    if (res1.getString(0).equals("positive")) {
                        countpos = res1.getFloat(1);
                    } else if (res1.getString(0).equals("negative")) {
                        countneg = res1.getFloat(1);
                    } else if (res1.getString(0).equals("neutral")) {
                        countneu = res1.getFloat(1);
                    }
                }


                xData = new String[]{"Positive", "Neutral", "Negative"};
                yData = new Float[]{countpos, countneu, countneg};
                mChart.setDrawHoleEnabled(true);
                mChart.setHoleRadius(50);
                mChart.setHoleColorTransparent(true);
                mChart.setTransparentCircleRadius(60);
                mChart.setTransparentCircleColor(Color.WHITE);
                mChart.setCenterText(new Integer((int) (countpos + countneu + countneg)) + "\nReviews");
                mChart.setCenterTextColor(ColorTemplate.getHoloBlue());
                mChart.setCenterTextSize(23);
                mChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
                mChart.setDescription("");


                ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                for (int i = 0; i < yData.length; i++)
                    yVals1.add(new Entry(yData[i], i));
                ArrayList<String> xVals = new ArrayList<String>();

                for (int i = 0; i < xData.length; i++)
                    xVals.add(xData[i]);

                //create pie data set

                PieDataSet dataSet = new PieDataSet(yVals1, " ");
                dataSet.setSliceSpace(3);
                dataSet.setSelectionShift(5);

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

                PieData data = new PieData(xVals, dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.BLACK);

                mChart.setData(data);
                mChart.setDescription("");

                //undo all highlights
                mChart.highlightValues(null);

                //update pie chart
                mChart.invalidate();
                //customize Legends
                Legend legit = mChart.getLegend();
                legit.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                legit.setXEntrySpace(7);
                legit.setYEntrySpace(5);

            }
        }

        public void GraphBoth() {
            mBarChart = new BarChart(getActivity());
            graphpage.addView(mBarChart);
            mBarChart.setBackgroundColor(Color.WHITE);
            mBarChart.setGridBackgroundColor(Color.WHITE);
            mBarChart.setDrawGridBackground(false);
            mBarChart.setDrawBarShadow(false);
            mBarChart.setDescription("");


            Float counthappy = 0f;
            Float countsad = 0f;
            Float countanger = 0f;
            Float countfear = 0f;
            Float countdisgust = 0f;
            Float countsurprise = 0f;

            Cursor res1 = mydb.countPosSentEmot();
            while (res1.moveToNext()) {
                if (res1.getString(0).equals("happy")) {
                    counthappy = Float.parseFloat(res1.getString(1));
                } else if (res1.getString(0).equals("sad")) {
                    countsad = Float.parseFloat(res1.getString(1));
                } else if (res1.getString(0).equals("anger")) {
                    countanger = Float.parseFloat(res1.getString(1));
                } else if (res1.getString(0).equals("fear")) {
                    countfear = Float.parseFloat(res1.getString(1));
                } else if (res1.getString(0).equals("disgust")) {
                    countdisgust = Float.parseFloat(res1.getString(1));
                } else if (res1.getString(0).equals("surprise")) {
                    countsurprise = Float.parseFloat(res1.getString(1));
                }
            }

            float forPercentage1 = counthappy + countsad + countanger + countfear + countdisgust + countsurprise;

            ArrayList<String> xVals = new ArrayList<String>();
            final String[] xData = {"Happy", "Sad", "Anger", "Fear", "Disg", "Surp"};
            for (int i = 0; i < xData.length; i++)
                xVals.add(xData[i]);
            float[] yData = {counthappy / forPercentage1 * 100, countsad / forPercentage1 * 100, countanger / forPercentage1 * 100,
                    countfear / forPercentage1 * 100, countdisgust / forPercentage1 * 100, countsurprise / forPercentage1 * 100};
            List<BarEntry> yVals1 = new ArrayList<>();
            for (int i = 0; i < yData.length; i++)
                yVals1.add(new BarEntry(yData[i], i));
            BarDataSet dataSet1 = new BarDataSet(yVals1, "Positive");
            dataSet1.setColor(ColorTemplate.getHoloBlue());

            Float pcounthappy = 0f;
            Float pcountsad = 0f;
            Float pcountanger = 0f;
            Float pcountfear = 0f;
            Float pcountdisgust = 0f;
            Float pcountsurprise = 0f;

            Cursor res2 = mydb.countNegSentEmot();
            while (res2.moveToNext()) {
                if (res2.getString(0).equals("happy")) {
                    pcounthappy = Float.parseFloat(res2.getString(1));
                } else if (res2.getString(0).equals("sad")) {
                    pcountsad = Float.parseFloat(res2.getString(1));
                } else if (res2.getString(0).equals("anger")) {
                    pcountanger = Float.parseFloat(res2.getString(1));
                } else if (res2.getString(0).equals("fear")) {
                    pcountfear = Float.parseFloat(res2.getString(1));
                } else if (res2.getString(0).equals("disgust")) {
                    pcountdisgust = Float.parseFloat(res2.getString(1));
                } else if (res2.getString(0).equals("surprise")) {
                    pcountsurprise = Float.parseFloat(res2.getString(1));
                } else if (res2.getString(0).equals("noemo")) {
                }
            }
            float forPercentage = counthappy + countsad + countanger + countfear + countdisgust + countsurprise;


            float[] yData1 = {pcounthappy / forPercentage * 100, pcountsad / forPercentage * 100, pcountanger / forPercentage * 100, pcountfear / forPercentage * 100,
                    pcountdisgust / forPercentage * 100, pcountsurprise / forPercentage * 100};
            List<BarEntry> yVals2 = new ArrayList<>();
            for (int i = 0; i < yData1.length; i++)
                yVals2.add(new BarEntry(yData1[i], i));
            BarDataSet dataSet2 = new BarDataSet(yVals2, "Negative");
            dataSet2.setColor(Color.RED);


            List<BarDataSet> list = new ArrayList<>();
            list.add(dataSet1);
            list.add(dataSet2);


            //

            //



            List wh = new ArrayList();
            wh.add(pcounthappy / forPercentage * 100);
            wh.add(pcountsad / forPercentage * 100);
            wh.add(pcountanger / forPercentage * 100);
            wh.add(pcountfear / forPercentage * 100);
            wh.add(pcountdisgust / forPercentage * 100);
            wh.add( pcountsurprise / forPercentage * 100);
            wh.add(counthappy / forPercentage1 * 100);
            wh.add(countsad / forPercentage1 * 100);
            wh.add( countanger / forPercentage1 * 100);
            wh.add( countfear / forPercentage1 * 100);
            wh.add(countdisgust / forPercentage1 * 100);
            wh.add(countsurprise / forPercentage1 * 100);

            float w = (float) Collections.max(wh);


            YAxis y1 = mBarChart.getAxisLeft();
            y1.setTextColor(Color.BLACK);

            //Y AXIS VALUE
            y1.setAxisMaxValue(w+10f);
            //
            y1.setDrawGridLines(false);
            y1.setTextSize(10);
            y1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v) {
                    String newResult = new Integer((int) v)+"%";
                    return newResult;
                }

            });


            YAxis y12 = mBarChart.getAxisRight();
            y12.setEnabled(false);
            XAxis x1 = mBarChart.getXAxis();
            x1.setDrawGridLines(false);
            x1.setPosition(XAxis.XAxisPosition.BOTTOM);
            BarData data1 = new BarData(xVals, list);
            mBarChart.setData(data1);
            mBarChart.setDrawValueAboveBar(true);
            Legend legit = mBarChart.getLegend();
            legit.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legit.setXEntrySpace(7);
            legit.setYEntrySpace(5);
        }
    }

}


