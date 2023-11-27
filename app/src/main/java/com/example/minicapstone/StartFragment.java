package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StartFragment extends Fragment {

    //Views
    View view;
    private TextView textStartDescription;
    Button btnContinue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_start, container, false);

        //Retrieve View ID's
        textStartDescription = view.findViewById(R.id.textStartDescription);
        btnContinue = view.findViewById(R.id.btnStartLogin);

        //Initialize Views
        textStartDescription.setText(R.string.helper_tool);
        btnContinue.setText(R.string.continue_button);

        //Set GoToSignup Button OnClickListener
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new LoginFragment(),null).commit();
            }
        });


        return view;
    }
}