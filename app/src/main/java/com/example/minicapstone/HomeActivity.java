package com.example.minicapstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth auth;

    //Class Attributes
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MeowBottomNavigation bottomNavigation;

    //Views
    private FrameLayout frameLayoutHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Firebase instance initialization
        auth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Retrieve View ID's
        frameLayoutHome = findViewById(R.id.frameLayoutHome);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        //Add Home Fragment
        if(savedInstanceState == null) {
            addHomeFragment();
        }

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_bar_chart));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_settings));

        bottomNavigation.show(2,true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                if(model.getId() == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome, new HomeFragment(),null).commit();
                } else if(model.getId() == 3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome, new SettingsFragment(),null).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutHome, new HomeFragment(),null).commit();
                }

                return null;
            }
        });

    }


    public void addHomeFragment() {
        fragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
    }


    //Replace Fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayoutHome, fragment, null)
                .commit();
    }
}