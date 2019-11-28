package com.example.mushroomtechnologymobile.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian3d;
import com.anychart.core.cartesian.series.Area3d;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.hatchfill.HatchFillType;
import com.example.mushroomtechnologymobile.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment implements
        OnChartValueSelectedListener {

    private HomeViewModel homeViewModel;
    //Typeface tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
    private LineChart chart,chart2;
    static String Database_Path = "nodemcu";
    DatabaseReference[] databaseReference = new DatabaseReference[2];
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        chart = root.findViewById(R.id.chart_suhu);
        chart2 = root.findViewById(R.id.chart_kelembapan);
        chart.setOnChartValueSelectedListener(this);
        chart2.setOnChartValueSelectedListener(this);

        // enable description text
        chart.getDescription().setEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);

        // enable description text
        chart2.getDescription().setEnabled(true);

        // enable touch gestures
        chart2.setTouchEnabled(true);

        // enable scaling and dragging
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart2.setPinchZoom(true);

        // set an alternative background color
        chart2.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);
        chart2.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        Legend l2 = chart2.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextColor(Color.WHITE);

        l2.setForm(Legend.LegendForm.LINE);
        //l2.setTypeface(tfLight);
        l2.setTextColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
        XAxis xl2 = chart2.getXAxis();
        //xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
       // xl2.setTypeface(tfLight);
        xl2.setTextColor(Color.WHITE);
        xl2.setDrawGridLines(false);
        xl2.setAvoidFirstLastClipping(true);
        xl2.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        YAxis leftAxis2 = chart2.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis2.setTextColor(Color.WHITE);
        leftAxis2.setAxisMaximum(100f);
        leftAxis2.setAxisMinimum(0f);
        leftAxis2.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        YAxis rightAxis2 = chart2.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis2.setEnabled(false);


        databaseReference[0] = FirebaseDatabase.getInstance().getReference(Database_Path+"/suhu");
        databaseReference[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getContext(),dataSnapshot.getValue(Float.class).toString(),Toast.LENGTH_LONG).show();
                if(dataSnapshot.exists() && dataSnapshot.getValue() != null){
                    LineData data = chart.getData();
                    if(data != null){
                        ILineDataSet set = data.getDataSetByIndex(0);
                        if (set == null){
                            set = createSet();
                            data.addDataSet(set);
                        }
                        data.addEntry(new Entry(set.getEntryCount(),dataSnapshot.getValue(Float.class)), 0);
                        data.notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.setVisibleXRangeMaximum(50);
                        chart.moveViewToX(data.getEntryCount());

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference[1] = FirebaseDatabase.getInstance().getReference(Database_Path+"/kelembapan");
        databaseReference[1].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    LineData data = chart2.getData();
                    if(data != null){
                        ILineDataSet set = data.getDataSetByIndex(0);
                        if (set == null){
                            set = createSet();
                            data.addDataSet(set);
                        }
                        data.addEntry(new Entry(set.getEntryCount(), dataSnapshot.getValue(Float.class)), 0);
                        data.notifyDataChanged();
                        chart2.notifyDataSetChanged();
                        chart2.setVisibleXRangeMaximum(50);
                        chart2.moveViewToX(data.getEntryCount());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }

    private void addEntry() {

        LineData data = chart.getData();
        LineData data2 = chart2.getData();

        if (data != null && data2 != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set2 = data2.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
                set2 = createSet2();
                data2.addDataSet(set2);
            }

            data.addEntry(new Entry(set.getEntryCount(), randFloat(10,40)), 0);
            data.notifyDataChanged();
            data2.addEntry(new Entry(set.getEntryCount(), randFloat(10,70)), 0);
            data2.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();
            chart2.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(50);
            chart2.setVisibleXRangeMaximum(100);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());
            chart2.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Suhu");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private LineDataSet createSet2() {

        LineDataSet set = new LineDataSet(null, "Kelembapan");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }
    private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                addEntry();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {

                    // Don't generate garbage runnables inside the loop.
                    if(getActivity() != null){
                        getActivity().runOnUiThread(runnable);
                    }


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }
    public  float randFloat(float min, float max) {

        Random rand = new Random();

        return rand.nextFloat() * (max - min) + min;

    }

    @Override
    public void onResume() {
        super.onResume();
        //feedMultiple();
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (thread != null) {
//            thread.interrupt();
//        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}