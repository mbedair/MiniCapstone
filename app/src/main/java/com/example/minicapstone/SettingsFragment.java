package com.example.minicapstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingsFragment extends Fragment {

    //Class Attributes
    View view;
    private EditText editSettingsNickname, editSettingsOldPassword, editSettingsNewPassword;
    private Button btnSaveSettings;

    private String nickname, oldPassword, newPassword;

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


        //Firebase updating data




        //Set SignUp Button OnClickListener
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nickname = editSettingsNickname.getText().toString().trim();
                oldPassword = editSettingsOldPassword.getText().toString().trim();
                newPassword = editSettingsNewPassword.getText().toString().trim();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();

                User userData = new User(nickname);

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.child(userId).setValue(userData);




                //Check if any of the input fields are left empty
                if (editSettingsNickname.length() < 1 || editSettingsOldPassword.length() < 1 || editSettingsNewPassword.length() < 1) {
                    Toast.makeText(getActivity(), "Please Fill In All Fields", Toast.LENGTH_SHORT).show();
                } else if (editSettingsNewPassword.length()<6 || editSettingsOldPassword.length()<6) {
                    Toast.makeText(getActivity(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Fatal error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(),null).commit();
//                    Toast.makeText(getActivity(), "Settings Updated", Toast.LENGTH_LONG).show();
                }

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