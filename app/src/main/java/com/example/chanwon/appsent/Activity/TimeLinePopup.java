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
        y1.setAxisMaxValue(100f);
        //
        y1.setDrawGridLines(true);

        YAxis y12 = mLineChart.getAxisRight();
        y12.setEnabled(false);
        AddEntry();


    }

    private void AddEntry() {

        ArrayList<String> xVals = new ArrayList<String>();
        String[] xData = {"Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"};
        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        Float countpos = 0f;
        Float countneg = 0f;
        Float countneu = 0f;

        Cursor res1 = mydb.getTimeSentiment("positive");
        Cursor res2 = mydb.getTimeSentiment("negative");
        Cursor res3 = mydb.getTimeSentiment("neutral");

        while (res1.moveToNext()) {
            countpos = Float.parseFloat(res1.getString(1));
        }
        while (res2.moveToNext()) {
            countneg = Float.parseFloat(res2.getString(1));
        }
        while (res3.moveToNext()) {
            countneu = Float.parseFloat(res3.getString(1));
        }

        float overallCount = countneg + countneu + countpos;
        double overallCount1 = (double) overallCount;
        double countpos1 = (double) countpos;
        double countpos2 = (countpos1/overallCount1)*100d;
        double countneg1 = (double) countneg;
        double countneg2 = (countneg1/overallCount1)*100d;
        double countneu1 = (double) countneu;
        double countneu2 = (countneu1/overallCount1)*100d;

        float[] yData = new float[]{(float) countpos2, 50f, 50f, 20f, 40f, 70f, 20f};
        float[] yData1 = new float[]{(float) countneg2, 40f, 30f, 70f, 55f, 25f, 60f};
        float[] yData2 = new float[]{(float) countneu2, 10f, 20f, 10f, 5f, 5f, 20f };
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        ArrayList<Entry> yVals3 = new ArrayList<Entry>();
        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));
        for (int i = 0; i < yData1.length; i++)
            yVals2.add(new Entry(yData1[i], i));
        for (int i = 0; i < yData2.length; i++)
            yVals3.add(new Entry(yData2[i], i));
        LineDataSet dataset = new LineDataSet(yVals1, "Positive");
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

        LineDataSet dataset1 = new LineDataSet(yVals2, "Negative");
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

        LineDataSet dataset2 = new LineDataSet(yVals3, "Neutral");
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

        List<LineDataSet> list = new ArrayList<>();
        list.add(dataset);
        list.add(dataset1);
        list.add(dataset2);

        LineData data3 = new LineData(xVals, list);

        mLineChart.setData(data3);


    }
}
