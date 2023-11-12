package com.example.minicapstone;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    //Class Attributes
    View view;

    //Views
    private WebView webViewVideoFeed;
    private TextView editHomeNickname;
    private TextView upperStatusText;
    private TextView lowerStatusText;
    private androidx.cardview.widget.CardView colorStatus;
    private String nickname;

    //Vectors to be later extracted from database

    double[] U;
    double[] V;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        upperStatusText = view.findViewById(R.id.posturestatusuppertext);
        lowerStatusText = view.findViewById(R.id.posturestatuslowertext);
        colorStatus = view.findViewById(R.id.posturestatuscolor);
        editHomeNickname = view.findViewById(R.id.editHomeNickname);


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userData = dataSnapshot.getValue(User.class);
                     nickname = userData.getNickname().toString();
                     editHomeNickname.setText(nickname);

                    // Use the retrieved data as needed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


        //Retrieve View ID's
        webViewVideoFeed = view.findViewById(R.id.webViewVideoFeed);

        //Setup WebView
        webViewVideoFeed.setWebViewClient(new WebViewClient());
        webViewVideoFeed.loadUrl("http://192.168.137.121:8081/");
        WebSettings webSettings=webViewVideoFeed.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewVideoFeed.getSettings().setLoadWithOverviewMode(true);
        webViewVideoFeed.getSettings().setUseWideViewPort(true);
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


    public class mywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    //Calculations on vectors to deduce if correct/wrong posture
    private void calculateAngle() {
        // Calculate dot product
        double dotProduct = (U[0] * V[0]) + (U[1] * V[1]);

        // Calculate magnitudes
        double magnitudeU = Math.sqrt(U[0] * U[0] + U[1] * U[1]);
        double magnitudeV = Math.sqrt(V[0] * V[0] + V[1] * V[1]);

        // Calculate cosine of the angle
        double cosineTheta = dotProduct / (magnitudeU * magnitudeV);

        // Calculate the angle in radians
        double angleDegree = Math.acos(cosineTheta);

        // Do something with the calculated angle if needed
        // For example, display it in a TextView or perform further actions

        if(angleDegree > 85 && angleDegree < 110){

            //Update the UI if good posture
            upperStatusText.setText("Good posture");
            lowerStatusText.setText("Keep your back straight");
            colorStatus.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greencustom));

        }
    }






}