package com.dc.appdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.protocol.HTTP;


public class MainActivity extends ActionBarActivity {
    public static String TAG="AppDemo";
    private PackageManager mPm = null;
    private Button mBtnSetAlarm = null;
    private Button mBtnSendIntent = null;
    private Button mBtnSetTimer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPm = getPackageManager();

        check_devices();

        mBtnSetAlarm = (Button)findViewById(R.id.btn_id_set_alarm);
        mBtnSetAlarm.setOnClickListener(mBtnListener);

        mBtnSendIntent = (Button)findViewById(R.id.btn_id_send_intent);
        mBtnSendIntent.setOnClickListener(mBtnListener);

        mBtnSetTimer = (Button)findViewById(R.id.btn_id_set_timer);
        mBtnSetTimer.setOnClickListener(mBtnListener);


    }


    public View.OnClickListener mBtnListener = new  View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_id_set_alarm:
                    createAlarm("work",8, 13);
                    break;
                case R.id.btn_id_send_intent:
                    sendIntent();
                    break;
                case R.id.btn_id_set_timer:
                    startTimer("timer",5);
                    break;
                default:
                    break;
            }

        }
    };


    public void startTimer(String message, int seconds) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_LENGTH, seconds)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void sendIntent() {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "this is text");
        sendIntent.setType(HTTP.PLAIN_TEXT_TYPE); // "text/plain" MIME type

        String title = getResources().getString(R.string.chooser_title);

        Intent chooser = Intent.createChooser(sendIntent, title);

        // Verify the original intent will resolve to at least one activity
        if (sendIntent.resolveActivity(mPm) != null) {
            startActivity(chooser);
        }
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(mPm) != null) {
            startActivity(intent);
        }
    }

    public boolean check_devices() {
        if (!mPm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
            // This device does not have a compass, turn off the compass feature
            //disableCompassFeature();
            Log.d(TAG, "no compass");
            return false;
        }
        if (!mPm.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER)) {
            Log.d(TAG, "no baromter");
            return false;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "system version lower than 4.4");
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
