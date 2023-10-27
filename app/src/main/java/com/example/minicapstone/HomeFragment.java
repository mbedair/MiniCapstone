package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HomeFragment extends Fragment {

    //Class Attributes
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Show Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }


    @Override
    public void onStop() {
        super.onStop();

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

}