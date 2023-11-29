package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MetricsFragment extends Fragment {

    // --------- Pie Chart --------- //
    String[] states = new String[2];
    int[] hours = {33,22};
    AnyChartView anyChartView;

    // ---------------------------- //

    View view;
    TextView textDateMetrics, textMetrics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_metrics, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        //Retrieve View ID's
        textDateMetrics = view.findViewById(R.id.textDate);
        textMetrics = view.findViewById(R.id.textMetrics);

        //Initialize Date TextView
        textDateMetrics.setText(getCurrentDate());

        //Pie Chart
        anyChartView = view.findViewById(R.id.any_chart_view);
        states[0] = getResources().getString(R.string.good_posture);
        states[1] = getResources().getString(R.string.bad_posture);
        setupPieChart();


        return view;
    }

    public void setupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i<states.length; i++){
            dataEntries.add(new ValueDataEntry(states[i],hours[i]));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);

        pie.palette(new String[]{"#FF4F2A", "#AAAAAA"});

        pie.legend()
                .position("top")
                .itemsLayout(LegendLayout.HORIZONTAL);

    }


    @Override
    public void onResume() {
        super.onResume();

        //Update Date TextView
        textDateMetrics.setText(getCurrentDate());
    }

    public String getCurrentDate() {

        String currentDate;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        currentDate = dayFormat.format(calendar.getTime());

        currentDate = currentDate.toUpperCase();

        return currentDate;
    }
}