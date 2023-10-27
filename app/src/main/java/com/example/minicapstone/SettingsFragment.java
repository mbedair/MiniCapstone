package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsFragment extends Fragment {

    //Class Attributes
    View view;
    private EditText editSettingsUsername, editSettingsEmail, editSettingsPassword;
    private Button btnSaveSettings, btnGoToHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Retrieve View ID's
        editSettingsUsername = view.findViewById(R.id.editSettingsUsername);
        editSettingsEmail = view.findViewById(R.id.editSettingsEmail);
        editSettingsPassword = view.findViewById(R.id.editSettingsPassword);
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);
        btnGoToHome = view.findViewById(R.id.btnGoToHome);

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().hide();

        //Set SignUp Button OnClickListener
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if any of the input fields are left empty
                if (editSettingsUsername.length() < 1 || editSettingsEmail.length() < 1 || editSettingsPassword.length() < 1) {
                    Toast.makeText(getActivity(), "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(),null).commit();
                    Toast.makeText(getActivity(), "Settings Updated", Toast.LENGTH_LONG).show();
                }

            }

        });

        //Set GoToLogin Button OnClickListener
        btnGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(),null).commit();
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onStop() {
        super.onStop();

        //Show Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

}