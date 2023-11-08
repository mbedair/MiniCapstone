package com.example.minicapstone;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

    private String nickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Show Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

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

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }


    @Override
    public void onStop() {
        super.onStop();

        //Hide Toolbar
        ((MainActivity)getActivity()).getSupportActionBar().show();
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

}