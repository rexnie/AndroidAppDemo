package com.dc.appdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CustomViewActivity extends AppCompatActivity {
    private static final String TAG = "CustomViewActivity";
    private CustomView mCustomView;
    private Thread mUpdateProgressThread;
    private boolean mThreadRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view2);

        mCustomView = (CustomView) findViewById(R.id.round_progress_bar);
        startRoundProgressBar();
    }

    protected void startRoundProgressBar() {
        mThreadRunning = true;
        mUpdateProgressThread = new Thread(new Runnable() {
            @Override
            public void run() {  //Run in Worker Thread
                while (mThreadRunning) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }

                    mCustomView.post(new Runnable() {
                        @Override
                        public void run() {  //UI thread
                            mCustomView.setProgress();
                            mCustomView.invalidate();
                        }
                    });
                }
            }
        });
        mUpdateProgressThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mThreadRunning = false;
        mUpdateProgressThread = null;
    }
}
