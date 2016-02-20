package com.example.chanwon.appsent.Activity.timeline.emotion;

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
public class TimeLineEmoSurRank extends Activity {

    DatabaseHelper mydb;
    private FrameLayout mainLayout;
    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        mydb = new DatabaseHelper(this);


        setContentView(R.layout.activity_timeline_popup_sent_pos_rank);

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
        mLineChart.setTouchEnabled(false);

        //get legend object
        Legend u = mLineChart.getLegend();

        //customize legend
        u.setForm(Legend.LegendForm.CIRCLE);
        u.setFormSize(5);
        u.setTextColor(Color.BLACK);
        u.setTextSize(8);
        u.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        u.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        u.setFormToTextSpace(0f);
        u.setYEntrySpace(10f);
        u.setXEntrySpace(2f);

        XAxis x1 = mLineChart.getXAxis();
        x1.setTextColor(Color.BLACK);
        x1.setDrawGridLines(true);
        x1.setAvoidFirstLastClipping(true);
        x1.setTextSize(8);


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
        Cursor positivelistedlist = mydb.getlistedList("surprisefeaturetable");
        ArrayList<String> rankedList = new ArrayList<>();
        ArrayList<String> xVariables = new ArrayList<>();
        ArrayList<String> xVariables2 = new ArrayList<>();
        ArrayList<String> xVariables3 = new ArrayList<>();

        ArrayList<Entry> rank12 = new ArrayList<>();
        ArrayList<Entry> rank22 = new ArrayList<>();
        ArrayList<Entry> rank32 = new ArrayList<>();
        ArrayList<Entry> rank42 = new ArrayList<>();
        ArrayList<Entry> rank52 = new ArrayList<>();
        ArrayList<Entry> rank62 = new ArrayList<>();
        ArrayList<Entry> rank72 = new ArrayList<>();
        ArrayList<Entry> rank82 = new ArrayList<>();
        ArrayList<Entry> rank92 = new ArrayList<>();
        ArrayList<Entry> rank102 = new ArrayList<>();

        ArrayList<Entry> rank1 = new ArrayList<>();
        ArrayList<Entry> rank2 = new ArrayList<>();
        ArrayList<Entry> rank3 = new ArrayList<>();
        ArrayList<Entry> rank4 = new ArrayList<>();
        ArrayList<Entry> rank5 = new ArrayList<>();
        ArrayList<Entry> rank6 = new ArrayList<>();
        ArrayList<Entry> rank7 = new ArrayList<>();
        ArrayList<Entry> rank8 = new ArrayList<>();
        ArrayList<Entry> rank9 = new ArrayList<>();
        ArrayList<Entry> rank10 = new ArrayList<>();


        Cursor res5 = mydb.getMonthList();
        while (res5.moveToNext()) {
            xVariables.add(res5.getString(0));
        }

        while (positivelistedlist.moveToNext()) {
            rankedList.add(positivelistedlist.getString(0));
        }
        if (rankedList.get(rankedList.size() - 1).equals("Overall")) {
            xVariables2 = new ArrayList<>(xVariables);
            for (int j = 0; j < rankedList.size(); j++) {
                Cursor res4 = mydb.getTimeLineSurpriseRank(rankedList.get(j));
                System.out.println(rankedList.get(j));
                int indexEnt = 0;
                while (res4.moveToNext()) {
                    if (j == 0) {
                        rank12.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 1) {
                        rank22.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 2) {
                        rank32.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 3) {
                        rank42.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 4) {
                        rank52.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 5) {
                        rank62.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 6) {
                        rank72.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 7) {
                        rank82.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 8) {
                        rank92.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 9) {
                        rank102.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    }
                }

            }
            rank1 = new ArrayList<>(rank12);
            rank2 = new ArrayList<>(rank22);
            rank3 = new ArrayList<>(rank32);
            rank4 = new ArrayList<>(rank42);
            rank5 = new ArrayList<>(rank52);
            rank6 = new ArrayList<>(rank62);
            rank7 = new ArrayList<>(rank72);
            rank8 = new ArrayList<>(rank82);
            rank9 = new ArrayList<>(rank92);
            rank10 = new ArrayList<>(rank102);

        } else {
            xVariables2 = new ArrayList<>((xVariables.subList(xVariables.size() - 4, xVariables.size())));
            for (int j = 0; j < rankedList.size(); j++) {
                Cursor res4 = mydb.getTimeLineSurpriseRank(rankedList.get(j));
                System.out.println(rankedList.get(j));
                int indexEnt = 4 - xVariables.size();
                while (res4.moveToNext()) {
                    if (j == 0) {
                        rank12.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 1) {
                        rank22.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 2) {
                        rank32.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 3) {
                        rank42.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 4) {
                        rank52.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 5) {
                        rank62.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 6) {
                        rank72.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 7) {
                        rank82.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 8) {
                        rank92.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    } else if (j == 9) {
                        rank102.add(new Entry(res4.getInt(1), indexEnt));
                        indexEnt++;
                    }
                }

            }
            rank1 = new ArrayList<>(rank12.subList(rank12.size() - 4, rank12.size()));
            rank2 = new ArrayList<>(rank22.subList(rank12.size() - 4, rank22.size()));
            rank3 = new ArrayList<>(rank32.subList(rank12.size() - 4, rank32.size()));
            rank4 = new ArrayList<>(rank42.subList(rank12.size() - 4, rank42.size()));
            rank5 = new ArrayList<>(rank52.subList(rank12.size() - 4, rank52.size()));
            rank6 = new ArrayList<>(rank62.subList(rank12.size() - 4, rank62.size()));
            rank7 = new ArrayList<>(rank72.subList(rank12.size() - 4, rank72.size()));
            rank8 = new ArrayList<>(rank82.subList(rank12.size() - 4, rank82.size()));
            rank9 = new ArrayList<>(rank92.subList(rank12.size() - 4, rank92.size()));
            rank10 = new ArrayList<>(rank102.subList(rank12.size() - 4, rank102.size()));
        }


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


        Integer colorcode = colors.get(0);

        LineDataSet dataset1 = new LineDataSet(rank1, rankedList.get(0));
        dataset1.setDrawCubic(true);
        dataset1.setCubicIntensity(0.2f);
        dataset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset1.setColor(colorcode);
        dataset1.setCircleColor(colorcode);
        dataset1.setLineWidth(2f);
        dataset1.setCircleSize(4f);
        dataset1.setFillAlpha(65);
        dataset1.setFillColor(colorcode);
        dataset1.setHighLightColor(colorcode);
        dataset1.setValueTextColor(colorcode);
        dataset1.setValueTextSize(10f);


        colorcode = colors.get(1);
        LineDataSet dataset2 = new LineDataSet(rank2, rankedList.get(1));
        dataset2.setDrawCubic(true);
        dataset2.setCubicIntensity(0.2f);
        dataset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset2.setColor(colorcode);
        dataset2.setCircleColor(colorcode);
        dataset2.setLineWidth(2f);
        dataset2.setCircleSize(4f);
        dataset2.setFillAlpha(65);
        dataset2.setFillColor(colorcode);
        dataset2.setHighLightColor(colorcode);
        dataset2.setValueTextColor(colorcode);
        dataset2.setValueTextSize(10f);

        colorcode = colors.get(2);
        LineDataSet dataset3 = new LineDataSet(rank3, rankedList.get(2));
        dataset3.setDrawCubic(true);
        dataset3.setCubicIntensity(0.2f);
        dataset3.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset3.setColor(colorcode);
        dataset3.setCircleColor(colorcode);
        dataset3.setLineWidth(2f);
        dataset3.setCircleSize(4f);
        dataset3.setFillAlpha(65);
        dataset3.setFillColor(colorcode);
        dataset3.setHighLightColor(colorcode);
        dataset3.setValueTextColor(colorcode);
        dataset3.setValueTextSize(10f);

        colorcode = colors.get(3);
        LineDataSet dataset4 = new LineDataSet(rank4, rankedList.get(3));
        dataset4.setDrawCubic(true);
        dataset4.setCubicIntensity(0.2f);
        dataset4.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset4.setColor(colorcode);
        dataset4.setCircleColor(colorcode);
        dataset4.setLineWidth(2f);
        dataset4.setCircleSize(4f);
        dataset4.setFillAlpha(65);
        dataset4.setFillColor(colorcode);
        dataset4.setHighLightColor(colorcode);
        dataset4.setValueTextColor(colorcode);
        dataset4.setValueTextSize(10f);

        colorcode = colors.get(4);
        LineDataSet dataset5 = new LineDataSet(rank5, rankedList.get(4));
        dataset5.setDrawCubic(true);
        dataset5.setCubicIntensity(0.2f);
        dataset5.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset5.setColor(colorcode);
        dataset5.setCircleColor(colorcode);
        dataset5.setLineWidth(2f);
        dataset5.setCircleSize(4f);
        dataset5.setFillAlpha(65);
        dataset5.setFillColor(colorcode);
        dataset5.setHighLightColor(colorcode);
        dataset5.setValueTextColor(colorcode);
        dataset5.setValueTextSize(10f);


        colorcode = colors.get(5);
        LineDataSet dataset6 = new LineDataSet(rank6, rankedList.get(5));
        dataset6.setDrawCubic(true);
        dataset6.setCubicIntensity(0.2f);
        dataset6.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset6.setColor(colorcode);
        dataset6.setCircleColor(colorcode);
        dataset6.setLineWidth(2f);
        dataset6.setCircleSize(4f);
        dataset6.setFillAlpha(65);
        dataset6.setFillColor(colorcode);
        dataset6.setHighLightColor(colorcode);
        dataset6.setValueTextColor(colorcode);
        dataset6.setValueTextSize(10f);

        colorcode = colors.get(6);
        LineDataSet dataset7 = new LineDataSet(rank7, rankedList.get(6));
        dataset7.setDrawCubic(true);
        dataset7.setCubicIntensity(0.2f);
        dataset7.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset7.setColor(colorcode);
        dataset7.setCircleColor(colorcode);
        dataset7.setLineWidth(2f);
        dataset7.setCircleSize(4f);
        dataset7.setFillAlpha(65);
        dataset7.setFillColor(colorcode);
        dataset7.setHighLightColor(colorcode);
        dataset7.setValueTextColor(colorcode);
        dataset7.setValueTextSize(10f);

        colorcode = colors.get(7);
        LineDataSet dataset8 = new LineDataSet(rank8, rankedList.get(7));
        dataset8.setDrawCubic(true);
        dataset8.setCubicIntensity(0.2f);
        dataset8.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset8.setColor(colorcode);
        dataset8.setCircleColor(colorcode);
        dataset8.setLineWidth(2f);
        dataset8.setCircleSize(4f);
        dataset8.setFillAlpha(65);
        dataset8.setFillColor(colorcode);
        dataset8.setHighLightColor(colorcode);
        dataset8.setValueTextColor(colorcode);
        dataset8.setValueTextSize(10f);


        colorcode = colors.get(8);
        LineDataSet dataset9 = new LineDataSet(rank9, rankedList.get(8));
        dataset9.setDrawCubic(true);
        dataset9.setCubicIntensity(0.2f);
        dataset9.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset9.setColor(colorcode);
        dataset9.setCircleColor(colorcode);
        dataset9.setLineWidth(2f);
        dataset9.setCircleSize(4f);
        dataset9.setFillAlpha(65);
        dataset9.setFillColor(colorcode);
        dataset9.setHighLightColor(colorcode);
        dataset9.setValueTextColor(colorcode);
        dataset9.setValueTextSize(10f);


        colorcode = colors.get(9);
        LineDataSet dataset10 = new LineDataSet(rank10, rankedList.get(9));
        dataset10.setDrawCubic(true);
        dataset10.setCubicIntensity(0.2f);
        dataset10.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset10.setColor(colorcode);
        dataset10.setCircleColor(colorcode);
        dataset10.setLineWidth(2f);
        dataset10.setCircleSize(4f);
        dataset10.setFillAlpha(65);
        dataset10.setFillColor(colorcode);
        dataset10.setHighLightColor(colorcode);
        dataset10.setValueTextColor(colorcode);
        dataset10.setValueTextSize(10f);


        List<LineDataSet> list = new ArrayList<>();

        list.add(dataset1);
        list.add(dataset2);
        list.add(dataset3);
        list.add(dataset4);
        list.add(dataset5);
        list.add(dataset6);
        list.add(dataset7);
        list.add(dataset8);
        list.add(dataset9);
        list.add(dataset10);


        LineData data3 = new LineData(xVariables2, list);

        mLineChart.setData(data3);


    }
}
