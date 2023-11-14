package com.example.minicapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth auth;

    //Class Attributes
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //Views
    private FrameLayout frameLayoutMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Firebase instance initialization
        auth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve View ID's
        frameLayoutMain = findViewById(R.id.frameLayoutMain);

        //Add Login Fragment
        if(savedInstanceState == null) {
            addStartFragment();
        }

    }


    //Add Home Actions Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }


    //Action Menu item handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_sign_out) {                 //If "Sign Out"
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new LoginFragment(),null).commit();

            //Logs the user out of the Firebase
            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Logout Successful", Toast.LENGTH_LONG).show();

            return true;
        }

        else if (item.getItemId() == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new SettingsFragment(),null).commit();
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }


    public void addStartFragment() {
        fragment = new StartFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutMain, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment() {
        fragment = new SignupFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
        fragmentTransaction.commit();
    }

}