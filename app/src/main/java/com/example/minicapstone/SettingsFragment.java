package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingsFragment extends Fragment {

    //Class Attributes
    View view;
    private EditText editSettingsNickname, editSettingsOldPassword, editSettingsNewPassword;
    private Button btnSaveSettings, btnGoToHome;

    private String nickname, oldpassword, newpassword;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Retrieve View ID's
        editSettingsNickname = view.findViewById(R.id.editSettingsNickname);
        editSettingsOldPassword = view.findViewById(R.id.editSettingsOldPassword);
        editSettingsNewPassword = view.findViewById(R.id.editSettingsNewPassword);
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);
        btnGoToHome = view.findViewById(R.id.btnGoToHome);


        //Firebase updating data




        //Set SignUp Button OnClickListener
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nickname = editSettingsNickname.getText().toString().trim();
                oldpassword = editSettingsOldPassword.getText().toString().trim();
                newpassword = editSettingsNewPassword.getText().toString().trim();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();

                User userData = new User(nickname);

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.child(userId).setValue(userData);



                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldpassword);

                //Check if any of the input fields are left empty
                if (editSettingsNickname.length() < 1 || editSettingsOldPassword.length() < 1 || editSettingsNewPassword.length() < 1) {
                    Toast.makeText(getActivity(), "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
                } else if (editSettingsNewPassword.length()<6 || editSettingsOldPassword.length()<6) {
                    Toast.makeText(getActivity(), "passwords are at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newpassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "password updated", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Fatal error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getActivity(), "password is incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(),null).commit();
//                    Toast.makeText(getActivity(), "Settings Updated", Toast.LENGTH_LONG).show();
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