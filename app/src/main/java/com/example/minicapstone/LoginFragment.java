package com.example.minicapstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private FirebaseAuth auth;

    //Class Attributes
    private String email, password;

    //Views
    View view;
    private EditText editLoginEmail, editLoginPassword;
    private Button btnLogin, btnGoToSignUp;
    private ImageButton imgBtnLoginBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //Retrieve View ID's
        editLoginEmail = view.findViewById(R.id.editLoginEmail);
        editLoginPassword = view.findViewById(R.id.editLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnGoToSignUp = view.findViewById(R.id.btnGoToSignUp);
        imgBtnLoginBack = view.findViewById(R.id.imgBtnLoginBack);


        //Set Login Button OnClickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Username and Password From Input Fields
                email = editLoginEmail.getText().toString().trim();
                password = editLoginPassword.getText().toString().trim();

                //Check if any of the input fields are left empty
                if (editLoginEmail.length() < 1 || editLoginPassword.length() < 1) {
                    Toast.makeText(getActivity(), "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
                } else if (editLoginPassword.length() < 6) {
                    Toast.makeText(getActivity(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent i = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
                                }
                            });


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


        //Set GoToSignup Button OnClickListener
        btnGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new SignupFragment(),null).commit();
            }
        });


        //Set GoToSignup Button OnClickListener
        imgBtnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new StartFragment(),null).commit();
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

}