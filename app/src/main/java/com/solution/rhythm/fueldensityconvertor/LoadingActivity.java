package com.solution.rhythm.fueldensityconvertor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingActivity extends AppCompatActivity {

    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Thread timerThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(400);
                }catch (Exception e) {
                    Log.i("Error", e.getMessage());
                }finally {
                    Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
        /*dbref = FirebaseDatabase.getInstance().getReference();

       dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Log.i("Temp",postSnapshot.child("Temp").getValue().toString());
                    if(postSnapshot.child("Temp").getValue().toString().equals("25"))
                    {
                        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
               /* if(isFound)
                {
                    Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database error",databaseError.getMessage());
            }
        });*/

    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
