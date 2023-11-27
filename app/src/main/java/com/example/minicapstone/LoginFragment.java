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
import android.widget.TextView;
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
    private TextView textHelloThere, textWelcomeBack, textHaveAccount;
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
        textHelloThere = view.findViewById(R.id.textHelloThere);
        textWelcomeBack = view.findViewById(R.id.textWelcomeBack);
        textHaveAccount = view.findViewById(R.id.textHaveAccount);
        editLoginEmail = view.findViewById(R.id.editLoginEmail);
        editLoginPassword = view.findViewById(R.id.editLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnGoToSignUp = view.findViewById(R.id.btnGoToSignUp);
        imgBtnLoginBack = view.findViewById(R.id.imgBtnLoginBack);

        //Initialize Views
        textHelloThere.setText(R.string.hello_there);
        textWelcomeBack.setText(R.string.welcome_back);
        textHaveAccount.setText(R.string.no_account);
        editLoginEmail.setHint(R.string.email_edit_hint_login);
        editLoginPassword.setHint(R.string.password_edit_hint_login);
        btnLogin.setText(R.string.login_button);
        btnGoToSignUp.setText(R.string.go_to_signup_button);


        //Set Login Button OnClickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Username and Password From Input Fields
                email = editLoginEmail.getText().toString().trim();
                password = editLoginPassword.getText().toString().trim();

                //Check if any of the input fields are left empty
                if (editLoginEmail.length() < 1 || editLoginPassword.length() < 1) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.fill_in_all_fields), Toast.LENGTH_SHORT).show();
                } else if (editLoginPassword.length() < 6) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.password_must_be_at_least), Toast.LENGTH_SHORT).show();
                } else {

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent i = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getActivity(), getContext().getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), getContext().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
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