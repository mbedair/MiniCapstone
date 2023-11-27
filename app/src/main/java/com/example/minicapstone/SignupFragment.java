package com.example.minicapstone;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class SignupFragment extends Fragment {

    private FirebaseAuth auth;

    //Class Attributes
    private String email, password, confirmPassword;

    //Views
    View view;
    private TextView textSignUp, textCreateAccount, textHaveAccount;
    private EditText editSignUpPassword, editSignUpEmail, editSignUpConfirmPassword;
    private Button btnSignUp, btnGoToLogin;
    ImageView imgBtnSignUpBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        //Retrieve View ID's
        textSignUp = view.findViewById(R.id.textSignUp);
        textCreateAccount = view.findViewById(R.id.textCreateAccount);
        textHaveAccount = view.findViewById(R.id.textHaveAccount);
        editSignUpEmail = view.findViewById(R.id.editSignUpEmail);
        editSignUpPassword = view.findViewById(R.id.editSignUpPassword);
        editSignUpConfirmPassword = view.findViewById(R.id.editSignUpConfirmPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnGoToLogin = view.findViewById(R.id.btnGoToLogin);
        imgBtnSignUpBack = view.findViewById(R.id.imgBtnSignUpBack);

        //Initialize Views
        textSignUp.setText(R.string.sign_up);
        textCreateAccount.setText(R.string.create_new_account);
        textHaveAccount.setText(R.string.have_account);
        editSignUpEmail.setHint(R.string.email_edit_hint_signup);
        editSignUpPassword.setHint(R.string.password_edit_hint_signup);
        editSignUpConfirmPassword.setHint(R.string.confirm_password_edit_hint_signup);
        btnSignUp.setText(R.string.signup_button);
        btnGoToLogin.setText(R.string.go_to_login_button);

        //Set SignUp Button OnClickListener
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = editSignUpEmail.getText().toString().trim();
                password = editSignUpPassword.getText().toString().trim();
                confirmPassword = editSignUpConfirmPassword.getText().toString().trim();

                //Check if any of the input fields are left empty
                if (editSignUpEmail.length() < 1 || editSignUpPassword.length() < 1) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.fill_in_all_fields), Toast.LENGTH_SHORT).show();
                } else if (editSignUpPassword.length() < 6) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.password_must_be_at_least), Toast.LENGTH_LONG).show();
                } else if(!password.equals(confirmPassword)) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show();
                } else {


                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new LoginFragment(),null).commit();
                                Toast.makeText(getActivity(), getContext().getString(R.string.account_created_successfully), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), getContext().getString(R.string.account_creation_failed), Toast.LENGTH_LONG).show();
                            }
                        }
                    });



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

        //Set SignUp Back Image Button OnClickListener
        imgBtnSignUpBack.setOnClickListener(new View.OnClickListener() {
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
    }


    @Override
    public void onStop() {
        super.onStop();
    }

}