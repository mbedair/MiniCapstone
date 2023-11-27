package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MetricsFragment extends Fragment {

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


        return view;
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