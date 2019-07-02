package com.solution.rhythm.fueldensityconvertor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference dbref;
    EditText tempEditText, densityEditText, standardEditText;
    String temp,density,standardDensity;
    private AdView mAdView;
    boolean isError;
    ProgressBar progressBar;

    public void CalculateDensity()
    {
        progressBar.setVisibility(View.VISIBLE);
        standardEditText.setVisibility(View.INVISIBLE);

         dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isFound = false;
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    if(postSnapshot.child("Temp").getValue().toString().equals(temp))
                    {
                        try {
                            standardDensity = postSnapshot.child(density).getValue().toString();
                            progressBar.setVisibility(View.INVISIBLE);
                            standardEditText.setVisibility(View.VISIBLE);
                            standardEditText.setText(standardDensity);
                            isFound = true;
                            break;
                        }catch (Exception e)
                        {

                        }
                    }
                }
                if(isFound==false)
                {
                    standardEditText.setText("Not Found");
                    progressBar.setVisibility(View.INVISIBLE);
                    standardEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database error",databaseError.getMessage());
            }
        });


    }

    public void buttonClicked(View view)
    {
        double valueTemp,valueDensity;
        isError=false;

        try {
            //Check for Density
            valueDensity = Double.parseDouble(densityEditText.getText().toString());
            int intDensity = (int) valueDensity;
            density = String.valueOf(intDensity);

            //Correct Temperature
            valueTemp = Double.parseDouble(tempEditText.getText().toString());
            int intTemp = (int) valueTemp;
            double decTemp = valueTemp - (double) intTemp;
            temp = String.valueOf(intTemp);

            if (decTemp >= 0.75) {
                temp += ".75";
            } else if (decTemp >= 0.5) {
                temp += ".5";
            } else if (decTemp >= 0.25) {
                temp += ".25";
            }

        }catch (Exception e)
        {
            isError=true;
            Toast.makeText(this,"Check Input Data",Toast.LENGTH_SHORT).show();
        }
        standardDensity = "0.0";


        //Hide Keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        if(!isError) {

            CalculateDensity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mobile Ads
        MobileAds.initialize(this, "ca-app-pub-9529062142998244~7141071504");



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        dbref = FirebaseDatabase.getInstance().getReference();

        tempEditText = (EditText) findViewById(R.id.tempEditText);
        densityEditText = (EditText) findViewById(R.id.densityEditText);
        standardEditText = (EditText) findViewById(R.id.standardEditText);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        tempEditText.setText("25");
        densityEditText.setText("730");
        standardEditText.setText("738.9");

        density = densityEditText.getText().toString();
        temp = tempEditText.getText().toString();
        }
}