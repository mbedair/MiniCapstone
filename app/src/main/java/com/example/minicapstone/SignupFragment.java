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


public class SignupFragment extends Fragment {

    //Class Attributes
    private String username, email, password;

    //Views
    View view;
    private EditText editSignUpUsername, editSignUpPassword;
    private Button btnSignUp, btnGoToLogin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        //Retrieve View ID's
        editSignUpUsername = view.findViewById(R.id.editSignUpUsername);
        editSignUpPassword = view.findViewById(R.id.editSignUpPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnGoToLogin = view.findViewById(R.id.btnGoToLogin);

        //Set SignUp Button OnClickListener
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if any of the input fields are left empty
                if (editSignUpUsername.length() < 1 || editSignUpPassword.length() < 1) {
                    Toast.makeText(getActivity(), "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new LoginFragment(),null).commit();
                    Toast.makeText(getActivity(), "Account Created Successfully", Toast.LENGTH_LONG).show();
                }
            }

        });

        //Set GoToLogin Button OnClickListener
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new LoginFragment(),null).commit();
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