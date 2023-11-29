package com.example.minicapstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
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
    private TextView textSettings, textNotifications, textUserSettings;
    private EditText editSettingsNickname, editSettingsOldPassword, editSettingsNewPassword, editConfirmSettingsNewPassword;
    private Button btnSaveSettings, btnSignOut;
    private Switch switchNotifications, switchEditSettings;

    private String nickname, oldPassword, newPassword, confirmPassword;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Retrieve View ID's
        textSettings = view.findViewById(R.id.textSettings);
        textNotifications = view.findViewById(R.id.textNotifications);
        textUserSettings = view.findViewById(R.id.textUserSettings);
        //editSettingsNickname = view.findViewById(R.id.editSettingsNickname);
        editSettingsOldPassword = view.findViewById(R.id.editSettingsOldPassword);
        editSettingsNewPassword = view.findViewById(R.id.editSettingsNewPassword);
        editConfirmSettingsNewPassword = view.findViewById(R.id.editConfirmSettingsNewPassword);
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        switchNotifications = view.findViewById(R.id.switchNotifications);
        switchEditSettings = view.findViewById(R.id.switchEditSettings);

        //Initialize Views
        textSettings.setText(R.string.settings);
        textNotifications.setText(R.string.notifications_description);
        textUserSettings.setText(R.string.user_settings);
        //editSettingsNickname.setHint(R.string.nickname_hint_settings);
        editSettingsOldPassword.setHint(R.string.old_password_hint_settings);
        editSettingsNewPassword.setHint(R.string.new_password_hint_settings);
        editConfirmSettingsNewPassword.setHint(R.string.confirm_password_edit_hint_settings);
        btnSaveSettings.setText(R.string.save_settings);
        btnSignOut.setText(R.string.sign_out);
        switchNotifications.setText(R.string.allow_notifications);
        switchEditSettings.setText(R.string.edit_password);


        //Firebase updating data




        //Set SignUp Button OnClickListener
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //nickname = editSettingsNickname.getText().toString().trim();
                oldPassword = editSettingsOldPassword.getText().toString().trim();
                newPassword = editSettingsNewPassword.getText().toString().trim();
                confirmPassword = editConfirmSettingsNewPassword.getText().toString().trim();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();

                User userData = new User(nickname);

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.child(userId).setValue(userData);




                //Check if any of the input fields are left empty
                if (/**editSettingsNickname.length() < 1 ||**/ editSettingsOldPassword.length() < 1 || editSettingsNewPassword.length() < 1) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.fill_in_all_fields), Toast.LENGTH_SHORT).show();
                } else if (editSettingsOldPassword.length() < 6) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.password_must_be_at_least), Toast.LENGTH_LONG).show();
                } else if(editSettingsNewPassword.length() < 6) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.new_password_must_be_at_least), Toast.LENGTH_LONG).show();
                } else if(!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getActivity(), getContext().getString(R.string.passwords_do_not_match), Toast.LENGTH_SHORT).show();
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
                                                            Toast.makeText(getActivity(), getContext().getString(R.string.password_updated), Toast.LENGTH_SHORT).show();
                                                            editSettingsOldPassword.setEnabled(false);
                                                            editSettingsNewPassword.setEnabled(false);
                                                            editConfirmSettingsNewPassword.setEnabled(false);
                                                            switchEditSettings.setChecked(false);
                                                        } else {
                                                            Toast.makeText(getActivity(), getContext().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getActivity(), getContext().getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new HomeFragment(),null).commit();
//                    Toast.makeText(getActivity(), "Settings Updated", Toast.LENGTH_LONG).show();
                }

            }

        });


        //Set Allow Background Notifications Switch Listener
        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //Enable Notifications
                } else {
                    //Disable Notifications
                }
            }
        });


        //Set Edit Password Switch Listener
        switchEditSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editSettingsOldPassword.setEnabled(true);
                    editSettingsNewPassword.setEnabled(true);
                    editConfirmSettingsNewPassword.setEnabled(true);
                } else {
                    editSettingsOldPassword.setEnabled(false);
                    editSettingsNewPassword.setEnabled(false);
                    editConfirmSettingsNewPassword.setEnabled(false);
                }
            }
        });


        //Set GoToLogin Button OnClickListener
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), getContext().getString(R.string.sign_out_successful), Toast.LENGTH_SHORT).show();
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