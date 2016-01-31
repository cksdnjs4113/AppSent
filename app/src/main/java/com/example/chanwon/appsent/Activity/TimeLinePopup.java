package com.example.chanwon.appsent.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

/**
 * Created by CHANWON on 7/31/2015.
 */
public class TimeLinePopup extends Activity {

    private FrameLayout mainLayout;
    private LineChart mLineChart;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        mydb = new DatabaseHelper(this);


        setContentView(R.layout.popupwindow);

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
        mLineChart.setHighlightEnabled(false);

        //enable touch gesture
        mLineChart.setTouchEnabled(true);
        //enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);

    //    enable pich zoom to avoid scaling x and y axis seprately

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
        u.setTextSize(10);
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
        Cursor res4 = mydb.countTimeSentiment();
        ArrayList<String> xVariables = new ArrayList<>();
        ArrayList<Entry> vpostiveVar = new ArrayList<>();
        ArrayList<Entry> positiveVar = new ArrayList<>();
        ArrayList<Entry> neutralVar = new ArrayList<>();
        ArrayList<Entry> negativeVar = new ArrayList<>();
        ArrayList<Entry> vnegativeVar = new ArrayList<>();
        Float vpos = 0f;
        Float pos = 0f;
        Float neu = 0f;
        Float neg = 0f;
        Float vneg = 0f;
        Float sumofsent = 0f;
        int indexEntry = 0;

        while (res4.moveToNext()){
            xVariables.add(res4.getString(0));
            vpos = Float.parseFloat(res4.getString(1));
            pos = Float.parseFloat(res4.getString(2));
            neu = Float.parseFloat(res4.getString(3));
            neg = Float.parseFloat(res4.getString(4));
            vneg = Float.parseFloat(res4.getString(5));
            sumofsent = vpos+pos+neu+neg+vneg;
            vpos = vpos / (float) sumofsent;
            pos = pos / (float) sumofsent;
            neu = neu / (float) sumofsent;
            neg = neg / (float) sumofsent;
            vneg = vneg / (float) sumofsent;

            pos = vpos+pos;
            neg = vneg + neg;


            positiveVar.add(new Entry(pos * 100f, indexEntry));
            neutralVar.add(new Entry(neu * 100f, indexEntry));
            negativeVar.add(new Entry(neg * 100f, indexEntry));



            indexEntry++;
        }

        LineDataSet dataset1 = new LineDataSet(positiveVar, "Positive");
        dataset1.setDrawCubic(true);
        dataset1.setCubicIntensity(0.2f);
        dataset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset1.setColor(ColorTemplate.getHoloBlue());
        dataset1.setCircleColor(ColorTemplate.getHoloBlue());
        dataset1.setLineWidth(2f);
        dataset1.setCircleSize(4f);
        dataset1.setFillAlpha(65);
        dataset1.setFillColor(ColorTemplate.getHoloBlue());
        dataset1.setHighLightColor(Color.rgb(0, 191, 255));
        dataset1.setValueTextColor(Color.WHITE);
        dataset1.setValueTextSize(10f);

        LineDataSet dataset2 = new LineDataSet(neutralVar, "Neutral");
        dataset2.setDrawCubic(true);
        dataset2.setCubicIntensity(0.2f);
        dataset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset2.setColor(Color.YELLOW);
        dataset2.setCircleColor(Color.YELLOW);
        dataset2.setLineWidth(2f);
        dataset2.setCircleSize(4f);
        dataset2.setFillAlpha(65);
        dataset2.setFillColor(ColorTemplate.getHoloBlue());
        dataset2.setHighLightColor(Color.rgb(255, 247, 140));
        dataset2.setValueTextColor(Color.WHITE);
        dataset2.setValueTextSize(10f);

        LineDataSet dataset3 = new LineDataSet(negativeVar, "Negative");
        dataset3.setDrawCubic(true);
        dataset3.setCubicIntensity(0.2f);
        dataset3.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset3.setColor(Color.RED);
        dataset3.setCircleColor(Color.RED);
        dataset3.setLineWidth(2f);
        dataset3.setCircleSize(4f);
        dataset3.setFillAlpha(65);
        dataset3.setFillColor(ColorTemplate.getHoloBlue());
        dataset3.setHighLightColor(Color.rgb(255, 69, 0));
        dataset3.setValueTextColor(Color.WHITE);
        dataset3.setValueTextSize(10f);


        List<LineDataSet> list = new ArrayList<>();

        list.add(dataset1);
        list.add(dataset2);
        list.add(dataset3);


        LineData data3 = new LineData(xVariables, list);

        mLineChart.setData(data3);


    }
}
