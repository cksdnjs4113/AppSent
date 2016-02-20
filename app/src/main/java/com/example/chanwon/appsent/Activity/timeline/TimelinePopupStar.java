package com.example.chanwon.appsent.Activity.timeline;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class TimelinePopupStar extends ActionBarActivity {


    DatabaseHelper mydb;
    private FrameLayout mainLayout;
    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        mydb = new DatabaseHelper(this);


        setContentView(R.layout.activity_timeline_popup_star);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .75));

        mainLayout = (FrameLayout) findViewById(R.id.timeLineLayout);


        //create line chart
        mLineChart = new LineChart(this);
        //add to main layout
        mainLayout.addView(mLineChart);

        // customize line chart
        mLineChart.setDescription("");
        mLineChart.setNoDataTextDescription("No data for the moment");

        //enable value highlighting
        mLineChart.setHighlightEnabled(true);

        //enable touch gesture
        mLineChart.setTouchEnabled(true);
        //enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);

        //enable pich zoom to avoid scaling x and y axis seprately

        mLineChart.setPinchZoom(true);

        //alternative background color

        mLineChart.setBackgroundColor(Color.WHITE);

        //now, we word on data
        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        //add data to line chart

        mLineChart.setData(data);

        //get legend object
        Legend u = mLineChart.getLegend();

        //customize legend
        u.setForm(Legend.LegendForm.LINE);
        u.setTextColor(Color.BLACK);

        XAxis x1 = mLineChart.getXAxis();
        x1.setTextColor(Color.BLACK);
        x1.setDrawGridLines(true);
        x1.setAvoidFirstLastClipping(true);


        YAxis y1 = mLineChart.getAxisLeft();
        y1.setTextColor(Color.BLACK);

        //Y AXIS VALUE
//        y1.setAxisMaxValue(100f);
        //
        y1.setDrawGridLines(true);

        YAxis y12 = mLineChart.getAxisRight();
        y12.setEnabled(false);
        AddEntry();


    }

    private void AddEntry() {
        Cursor res4 = mydb.countTimeStars();
        ArrayList<String> xVariables = new ArrayList<>();
        ArrayList<Entry> star1 = new ArrayList<>();
        ArrayList<Entry> star2 = new ArrayList<>();
        ArrayList<Entry> star3 = new ArrayList<>();
        ArrayList<Entry> star4 = new ArrayList<>();
        ArrayList<Entry> star5 = new ArrayList<>();

        Float sta1 = 0f;
        Float sta2 = 0f;
        Float sta3 = 0f;
        Float sta4 = 0f;
        Float sta5 = 0f;

        Float sumofsent = 0f;
        int indexEntry = 0;

        while (res4.moveToNext()){
            xVariables.add(res4.getString(0));
            sta1 = Float.parseFloat(res4.getString(1));
            sta2 = Float.parseFloat(res4.getString(2));
            sta3 = Float.parseFloat(res4.getString(3));
            sta4 = Float.parseFloat(res4.getString(4));
            sta5 = Float.parseFloat(res4.getString(5));

            sumofsent = sta1+sta2+sta3+sta4+sta5;
            sta1 = sta1 / (float) sumofsent;
            sta2 = sta2 / (float) sumofsent;
            sta3 = sta3 / (float) sumofsent;
            sta4 = sta4 / (float) sumofsent;
            sta5 = sta5 / (float) sumofsent;


            star1.add(new Entry(sta1 * 100f, indexEntry));
            star2.add(new Entry(sta2 * 100f, indexEntry));
            star3.add(new Entry(sta3 * 100f, indexEntry));
            star4.add(new Entry(sta4 * 100f, indexEntry));
            star5.add(new Entry(sta5 * 100f, indexEntry));


            indexEntry++;
        }


        LineDataSet dataset = new LineDataSet(star1, "1 STAR");
        dataset.setDrawCubic(true);
        dataset.setCubicIntensity(0.2f);
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setColor(ColorTemplate.getHoloBlue());
        dataset.setCircleColor(ColorTemplate.getHoloBlue());
        dataset.setLineWidth(2f);
        dataset.setCircleSize(4f);
        dataset.setFillAlpha(65);
        dataset.setFillColor(ColorTemplate.getHoloBlue());
        dataset.setHighLightColor(Color.rgb(244, 177, 177));
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueTextSize(10f);

        LineDataSet dataset1 = new LineDataSet(star2, "2 STAR");
        dataset1.setDrawCubic(true);
        dataset1.setCubicIntensity(0.2f);
        dataset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset1.setColor(Color.RED);
        dataset1.setCircleColor(Color.RED);
        dataset1.setLineWidth(2f);
        dataset1.setCircleSize(4f);
        dataset1.setFillAlpha(65);
        dataset1.setFillColor(ColorTemplate.getHoloBlue());
        dataset1.setHighLightColor(Color.rgb(244, 177, 177));
        dataset1.setValueTextColor(Color.WHITE);
        dataset1.setValueTextSize(10f);

        LineDataSet dataset2 = new LineDataSet(star3, "3 STAR");
        dataset2.setDrawCubic(true);
        dataset2.setCubicIntensity(0.2f);
        dataset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset2.setColor(Color.YELLOW);
        dataset2.setCircleColor(Color.YELLOW);
        dataset2.setLineWidth(2f);
        dataset2.setCircleSize(4f);
        dataset2.setFillAlpha(65);
        dataset2.setFillColor(ColorTemplate.getHoloBlue());
        dataset2.setHighLightColor(Color.rgb(244, 177, 177));
        dataset2.setValueTextColor(Color.WHITE);
        dataset2.setValueTextSize(10f);

        LineDataSet dataset3 = new LineDataSet(star4, "4 STAR");
        dataset3.setDrawCubic(true);
        dataset3.setCubicIntensity(0.2f);
        dataset3.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset3.setColor(Color.BLUE);
        dataset3.setCircleColor(Color.BLUE);
        dataset3.setLineWidth(2f);
        dataset3.setCircleSize(4f);
        dataset3.setFillAlpha(65);
        dataset3.setFillColor(ColorTemplate.getHoloBlue());
        dataset3.setHighLightColor(Color.rgb(244, 177, 177));
        dataset3.setValueTextColor(Color.WHITE);
        dataset3.setValueTextSize(10f);

        LineDataSet dataset4 = new LineDataSet(star5, "5 STAR");
        dataset4.setDrawCubic(true);
        dataset4.setCubicIntensity(0.2f);
        dataset4.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset4.setColor(Color.CYAN);
        dataset4.setCircleColor(Color.CYAN);
        dataset4.setLineWidth(2f);
        dataset4.setCircleSize(4f);
        dataset4.setFillAlpha(65);
        dataset4.setFillColor(ColorTemplate.getHoloBlue());
        dataset4.setHighLightColor(Color.rgb(244, 177, 177));
        dataset4.setValueTextColor(Color.WHITE);
        dataset4.setValueTextSize(10f);


//        LineDataSet dataset6 = new LineDataSet(yVals7, "No Emo");
//        dataset6.setDrawCubic(true);
//        dataset6.setCubicIntensity(0.2f);
//        dataset6.setAxisDependency(YAxis.AxisDependency.LEFT);
//        dataset6.setColor(Color.YELLOW);
//        dataset6.setCircleColor(Color.YELLOW);
//        dataset6.setLineWidth(2f);
//        dataset6.setCircleSize(4f);
//        dataset6.setFillAlpha(65);
//        dataset6.setFillColor(ColorTemplate.getHoloBlue());
//        dataset6.setHighLightColor(Color.rgb(244, 177, 177));
//        dataset6.setValueTextColor(Color.WHITE);
//        dataset6.setValueTextSize(10f);

        List<LineDataSet> list = new ArrayList<>();
        list.add(dataset);
        list.add(dataset1);
        list.add(dataset2);
        list.add(dataset3);
        list.add(dataset4);


        LineData data3 = new LineData(xVariables, list);

        mLineChart.setData(data3);


    }
}