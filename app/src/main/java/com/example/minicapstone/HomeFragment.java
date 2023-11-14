package com.example.minicapstone;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
    private static final String CHANNEL_ID = "channelid1";

    //Vectors to be later extracted from database

    double[] uLeft = new double[2];
    double[] vLeft = new double[2];
    double[] uRight = new double[2];
    double[] vRight = new double[2];

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
        sendNotifications(view);


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

        // Get a reference to the "pose_data" node
        DatabaseReference poseDataRef = FirebaseDatabase.getInstance().getReference("pose_data");

        poseDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Extract values from "vector_11_to_23"
                DataSnapshot vectorLeft1Snap = dataSnapshot.child("vector_11_to_23");
                double value0LeftVector1 = vectorLeft1Snap.child("0").getValue(Double.class);
                double value1LeftVector1 = vectorLeft1Snap.child("1").getValue(Double.class);

                // Extract values from "vector_23_to_25"
                DataSnapshot vectorLeft2Snap = dataSnapshot.child("vector_23_to_25");
                double value0LeftVector2 = vectorLeft2Snap.child("0").getValue(Double.class);
                double value1LeftVector2 = vectorLeft2Snap.child("1").getValue(Double.class);

                // Log values for vector_11_to_23
                Log.d("TAG", "vector_11_to_23 - Value 0: " + value0LeftVector1 + ", Value 1: " + value1LeftVector1);

                // Log values for vector_23_to_25
                Log.d("TAG", "vector_23_to_25 - Value 0: " + value0LeftVector2 + ", Value 1: " + value1LeftVector2);


                // Extract values from "vector_12_to_24"
                DataSnapshot vectorRight1Snap = dataSnapshot.child("vector_12_to_24");
                double value0RightVector1 = vectorRight1Snap.child("0").getValue(Double.class);
                double value1RightVector1 = vectorRight1Snap.child("1").getValue(Double.class);

                // Extract values from "vector_24_to_26"
                DataSnapshot vectorRight2Snap = dataSnapshot.child("vector_24_to_26");
                double value0RightVector2 = vectorRight2Snap.child("0").getValue(Double.class);
                double value1RightVector2 = vectorRight2Snap.child("1").getValue(Double.class);

                // Log values for vector_12_to_24
                Log.d("TAG", "vector_12_to_24 - Value 0: " + value0RightVector1 + ", Value 1: " + value1RightVector1);

                // Log values for vector_24_to_26
                Log.d("TAG", "vector_24_to_26 - Value 0: " + value0RightVector2 + ", Value 1: " + value1RightVector2);



                // Store values in U and V arrays for further calculations
                uLeft[0] = value0LeftVector1;
                uLeft[1] = value1LeftVector1;

                vLeft[0] = value0LeftVector2;
                vLeft[1] = value1LeftVector2;

                uRight[0] = value0RightVector1;
                uRight[1] = value1RightVector1;

                vRight[0] = value0RightVector2;
                vRight[1] = value1RightVector2;

                // Call calculateAngle after updating U and V
                calculateAngle();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error: " + databaseError.getMessage());
            }
        });


        //Retrieve View ID's
        webViewVideoFeed = view.findViewById(R.id.webViewVideoFeed);

        //Setup WebView
        webViewVideoFeed.setWebViewClient(new WebViewClient());
        webViewVideoFeed.loadUrl("http://192.168.0.24:5000/");
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
        // Calculate angle for the left vectors
        double angleLeft = calculateAngleBetweenVectors(uLeft, vLeft);

        // Calculate angle for the right vectors
        double angleRight = calculateAngleBetweenVectors(uRight, vRight);

        // Update the UI based on both left and right angles
        if ((angleLeft > 85 && angleLeft < 110) || (angleRight > 85 && angleRight < 110)) {
            // Update the UI if good posture
            upperStatusText.setText("Good posture");
            lowerStatusText.setText("Keep your back straight");
            colorStatus.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greencustom));
        } else {
            // Update the UI for other cases (e.g., bad posture)
            upperStatusText.setText("Bad posture");
            lowerStatusText.setText("Straighten your back");
            colorStatus.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orangeCustom3));
            sendNotifications(view);
        }
    }

    // Helper method to calculate angle between two vectors
    private double calculateAngleBetweenVectors(double[] u, double[] v) {
        // Calculate dot product
        double dotProduct = (u[0] * v[0]) + (u[1] * v[1]);

        // Calculate magnitudes
        double magnitudeU = Math.sqrt(u[0] * u[0] + u[1] * u[1]);
        double magnitudeV = Math.sqrt(v[0] * v[0] + v[1] * v[1]);

        // Calculate cosine of the angle
        double cosineTheta = dotProduct / (magnitudeU * magnitudeV);

        // Calculate the angle in radians
        double angleRad = Math.acos(cosineTheta);

        // Convert radians to degrees
        double angleDegree = Math.toDegrees(angleRad);

        return angleDegree;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Posture", importance);
            channel.setDescription("Sends a notification whenever your posture is not healthy!");
            NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            channel.setShowBadge(true);
        }
    }

    @SuppressLint("MissingPermission")
    public void sendNotifications(View view) {

        Log.d("Notification", "Sending notification");
        // Notification creation code
        Notification builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Please fix your posture!")
                .setContentText("Please open the app to check how to fix your posture")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireActivity().getApplicationContext());
        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder);
    }

}