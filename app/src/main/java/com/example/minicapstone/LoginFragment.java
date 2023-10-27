package com.example.minicapstone;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    //Class Attributes
    private String username, password;

    //Views
    View view;
    private EditText editLoginUsername, editLoginPassword;
    private Button btnLogin, btnGoToSignUp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //Retrieve View ID's
        editLoginUsername = view.findViewById(R.id.editLoginUsername);
        editLoginPassword = view.findViewById(R.id.editLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnGoToSignUp = view.findViewById(R.id.btnGoToSignUp);

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().hide();


        //Set Login Button OnClickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Username and Password From Input Fields
                username = editLoginUsername.getText().toString();
                password = editLoginPassword.getText().toString();

                //Check if any of the input fields are left empty
                if (editLoginUsername.length() < 1 || editLoginPassword.length() < 1) {
                    Toast.makeText(getActivity(), "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(),null).commit();
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_LONG).show();
                }
            }

        });


        //Set GoToSignup Button OnClickListener
        btnGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new SignupFragment(),null).commit();
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