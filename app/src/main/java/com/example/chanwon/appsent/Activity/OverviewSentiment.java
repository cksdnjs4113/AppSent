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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chanwon.appsent.Activity.timeline.TimeLinePopup;
import com.example.chanwon.appsent.Activity.timeline.TimelinePopupEmo;
import com.example.chanwon.appsent.Activity.timeline.TimelinePopupStar;
import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.NavigationDrawer;
import com.example.chanwon.appsent.R;
import com.example.chanwon.appsent.Tab.SlidingTabLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.io.IOException;
import java.util.ArrayList;

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

    public static class MyFragment extends Fragment {


        PieChart mChart;
        Legend mLegend;
        BarChart mBarChart;
        DatabaseHelper mydb;
        private TextView textView, textView2, textView1;
        private FrameLayout graphpage, graphpage1;
        private Button buttonTimeline;
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
            View layout = inflater.inflate(R.layout.fragment_rankings, container, false);
            graphpage = (FrameLayout) layout.findViewById(R.id.graphframe);
            textView = (TextView) layout.findViewById(R.id.graphTitle);
            textView2 = (TextView) layout.findViewById(R.id.subTitle);
            buttonTimeline = (Button) layout.findViewById(R.id.buttonTimeline);
            spinner = (Spinner) layout.findViewById(R.id.spinner);
            spinner.setBackgroundColor(Color.LTGRAY);
            mydb = new DatabaseHelper(getActivity());

            final ArrayList<String> monthlist = new ArrayList<>();
            monthlist.add("Overall");
            monthlist.add("Last 4 Months");
//            Cursor res1 = mydb.getMonthList();
//            while (res1.moveToNext()) {
//                monthlist.add("Last 4 Months");
//            }

//            final Bundle bundle1 = getArguments();
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.values, monthlist);
            spinner.setAdapter(adapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle1 = getArguments();
                    if (bundle1 != null) {
                        if (bundle1.getInt("position") == 0) {
                            graphpage.removeAllViews();
                            textView.setText("Flutter");
                            textView2.setText("Overall Star Percentages");
                            String monthName = monthlist.get(position);
                            graphMethodStars(monthName);
                            timeline();
                            // timeline();

                        } else if (bundle1.getInt("position") == 1) {
                            graphpage.removeAllViews();
                            textView.setText("Flutter");
                            textView2.setText("Overall Sentiment Percentages");
                            String monthName = monthlist.get(position);
                            graphMethod(monthName);
                            timeline();
                            // timeline();

                        } else if (bundle1.getInt("position") == 2) {
                            graphpage.removeAllViews();
                            textView.setText("Flutter");
                            textView2.setText("Overall Emotion Percentages");
                            String monthName = monthlist.get(position);
                            try {
                                graphMethodForEmotion(monthName);
                                timeline();
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
                            textView.setText("Flutter");
                            textView2.setText("Overall Stars Percentages");
                            graphMethodStars("Overall");
                            timeline();
                            // timeline();

                        } else if (bundle1.getInt("position") == 1) {
                            textView.setText("Flutter");
                            textView2.setText("Overall Sentiment Percentages");
                            graphMethod("Overall");
                            timeline();
                            // timeline();

                        } else if (bundle1.getInt("position") == 2) {
                            textView.setText("Flutter");
                            textView2.setText("Overall Emotion Percentages");
                            try {
                                graphMethodForEmotion("Overall");
                                timeline();
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

        public void timeline() {
            buttonTimeline.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = getArguments();
                            if (bundle.getInt("position") == 0) {
                                startActivity(new Intent(getActivity(), TimelinePopupStar.class));
                            } else if (bundle.getInt("position") == 1) {
                                startActivity(new Intent(getActivity(), TimeLinePopup.class));
                            } else if (bundle.getInt("position") == 2) {
                                startActivity(new Intent(getActivity(), TimelinePopupEmo.class));
                            }
                        }
                    }
            );
        }

        public void graphMethodForEmotion(String position) throws IOException {
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
                mChart.setTouchEnabled(false);
                mChart.setRotationAngle(0);
                mChart.setRotationEnabled(true);
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
                ArrayList<String> last4month = new ArrayList();
                int allsentences = 0;
                if (position.equals("Overall")) {
                    Cursor res1 = mydb.countEmotion();
                    Cursor res2 = mydb.countAllSentencesStars();
                    while (res2.moveToNext()) {
                        allsentences = res2.getInt(0);
                    }
                    while (res1.moveToNext()) {
                        counthappy = res1.getFloat(0);
                        countsad = res1.getFloat(1);
                        countanger = res1.getFloat(2);
                        countfear = res1.getFloat(3);
                        countdisgust = res1.getFloat(4);
                        countsurprise = res1.getFloat(5);
                    }
                } else {
                    Cursor res1 = mydb.countEmotionforMonth();
                    Cursor res2 = mydb.countAllSentencesStarsbyMonth();
                    while (res2.moveToNext()) {
                        allsentences = res2.getInt(0);
                    }
                    while (res1.moveToNext()) {
                        counthappy = res1.getFloat(0);
                        countsad = res1.getFloat(1);
                        countanger = res1.getFloat(2);
                        countfear = res1.getFloat(3);
                        countdisgust = res1.getFloat(4);
                        countsurprise = res1.getFloat(5);
                    }
                }


                xData = new String[]{"Happy", "Sad", "Anger", "Fear", "Disgust", "Surprise"};
                yData = new Float[]{counthappy, countsad, countanger, countfear, countdisgust, countsurprise};
                mChart.setCenterText(allsentences + "\nReviews");
                mChart.setCenterTextColor(Color.BLACK);
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

        public void graphMethod(String position) {
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
                        Toast.makeText(getActivity(), xData[entry.getXIndex()] + "=" + new Integer((int) entry.getVal()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }

                });
                ///
                Float countvpos = 0f;
                Float countvneg = 0f;
                Float countpos = 0f;
                Float countneu = 0f;
                Float countneg = 0f;
                Integer allsentences = 0;
                if (position.equals("Overall")) {
                    Cursor res1 = mydb.countSentiment();
                    Cursor res2 = mydb.countAllSentencesStars();
                    while (res2.moveToNext()) {
                        allsentences = res2.getInt(0);
                    }
                    while (res1.moveToNext()) {
                        countvneg = res1.getFloat(0);
                        countneg = res1.getFloat(1);
                        countneu = res1.getFloat(2);
                        countpos = res1.getFloat(3);
                        countvpos = res1.getFloat(4);

                        countpos = countpos + countvpos;
                        countneg = countneg + countvneg;
                    }
                } else {
                    Cursor res1 = mydb.countSentimentbyMonth();
                    Cursor res2 = mydb.countAllSentencesStarsbyMonth();
                    while (res2.moveToNext()) {
                        allsentences = res2.getInt(0);
                    }
                    while (res1.moveToNext()) {
                        countvneg = res1.getFloat(0);
                        countneg = res1.getFloat(1);
                        countneu = res1.getFloat(2);
                        countpos = res1.getFloat(3);
                        countvpos = res1.getFloat(4);

                        countpos = countpos + countvpos;
                        countneg = countneg + countvneg;
                    }
                }


                xData = new String[]{"Positive", "Neutral", "Negative"};
                yData = new Float[]{countpos, countneu, countneg};
                mChart.setDrawHoleEnabled(true);
                mChart.setHoleRadius(50);
                mChart.setHoleColorTransparent(true);
                mChart.setTransparentCircleRadius(60);
                mChart.setTransparentCircleColor(Color.WHITE);
                mChart.setCenterText(allsentences + "\nReviews");
                mChart.setCenterTextColor(Color.BLACK);
                mChart.setCenterTextSize(23);
                mChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
                mChart.setDescription("");
                mChart.setTouchEnabled(false);

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
                colors.add(Color.rgb(0, 191, 255));
                colors.add(Color.rgb(255, 247, 140));
                colors.add(Color.rgb(255, 69, 0));
//                colors.add(Color.RED);
//                for (int c : ColorTemplate.VORDIPLOM_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.JOYFUL_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.COLORFUL_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.PASTEL_COLORS)
//                    colors.add(c);

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

        public void graphMethodStars(String position) {
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
                        Toast.makeText(getActivity(), xData[entry.getXIndex()] + "=" + new Integer((int) entry.getVal()), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }

                });
                ///
                Float stars1 = 0f;
                Float stars2 = 0f;
                Float stars3 = 0f;
                Float stars4 = 0f;
                Float stars5 = 0f;
                Integer allsentences = 0;
                if (position.equals("Overall")) {
                    Cursor res1 = mydb.countStars();
                    Cursor res2 = mydb.countAllSentencesStars();
                    while (res2.moveToNext()) {
                        allsentences = res2.getInt(0);
                    }
                    while (res1.moveToNext()) {
                        stars1 = res1.getFloat(0);
                        stars2 = res1.getFloat(1);
                        stars3 = res1.getFloat(2);
                        stars4 = res1.getFloat(3);
                        stars5 = res1.getFloat(4);

                    }
                } else {
                    Cursor res1 = mydb.countStarsbyMonth();
                    Cursor res2 = mydb.countAllSentencesStarsbyMonth();
                    while (res2.moveToNext()) {
                        allsentences = res2.getInt(0);
                    }
                    while (res1.moveToNext()) {
                        stars1 = res1.getFloat(0);
                        stars2 = res1.getFloat(1);
                        stars3 = res1.getFloat(2);
                        stars4 = res1.getFloat(3);
                        stars5 = res1.getFloat(4);

                    }
                }


                xData = new String[]{"1 STAR", "2 STAR", "3 STAR", "4 STAR", "5 STAR"};
                yData = new Float[]{stars1, stars2, stars3, stars4, stars5};
                mChart.setDrawHoleEnabled(true);
                mChart.setHoleRadius(50);
                mChart.setHoleColorTransparent(true);
                mChart.setTransparentCircleRadius(60);
                mChart.setTransparentCircleColor(Color.WHITE);
                mChart.setCenterText(allsentences + "\nReviews");
                mChart.setCenterTextColor(Color.BLACK);
                mChart.setCenterTextSize(23);
                mChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
                mChart.setDescription("");
                mChart.setTouchEnabled(false);

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
//                colors.add(Color.rgb(0,191,255));
//                colors.add(Color.rgb(255,247,140));
//                colors.add(Color.rgb(255,69,0));
//                colors.add(Color.RED);
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

}


