package com.solution.rhythm.fueldensityconvertor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 8/20/2017.
 */

public class SplashScreen extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timerThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                }catch (Exception e) {
                    Log.i("Error", e.getMessage());
                }finally {
                    Intent intent = new Intent(SplashScreen.this,CalcActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
