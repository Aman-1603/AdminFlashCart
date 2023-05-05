package com.example.adminflashcart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class GraphData_Fragment extends Fragment {


    public GraphData_Fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_data_, container, false);


      loadPiechart1(view);
      loadPiechart2(view);


        return view;
    }


    private void loadPiechart1(View view) {
        PieChart pieChart = view.findViewById(R.id.pie_chart);

        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(30f, "Entry 1"));
        pieEntries.add(new PieEntry(20f, "Entry 2"));
        pieEntries.add(new PieEntry(50f, "Entry 3"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Pie Chart");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }

    private void loadPiechart2(View view) {
        PieChart pieChart = view.findViewById(R.id.pie_chart1);

        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(30f, "Entry 1"));
        pieEntries.add(new PieEntry(20f, "Entry 2"));
        pieEntries.add(new PieEntry(50f, "Entry 3"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Pie Chart");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }

}