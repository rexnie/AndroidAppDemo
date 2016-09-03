package com.dc.appdemo;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_WRITE = 10;
    private static final String PER_WRITE_SDCARD =
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String TAG="AppDemo";
    private PackageManager mPm;
    public View.OnClickListener mBtnListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_id_set_alarm:
                    createAlarm("work", 8, 13);
                    break;
                case R.id.btn_id_send_intent:
                    sendIntent();
                    break;
                case R.id.btn_id_set_timer:
                    startTimer("timer", 5);
                    break;
                case R.id.btn_id_add_calendar_event:
                    break;
                case R.id.btn_id_rt_permissions:
                    requestPermission();
                    break;
                default:
                    break;
            }
        }
    };
    private Button mBtnSetAlarm;
    private Button mBtnSendIntent;
    private Button mBtnSetTimer;
    private Button mBtnAddCalendarEvent;
    private Button mBtnRuntimePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPm = getPackageManager();

        check_devices();
        initViews();
    }

    private void initViews() {
        mBtnSetAlarm = (Button)findViewById(R.id.btn_id_set_alarm);
        mBtnSetAlarm.setOnClickListener(mBtnListener);

        mBtnSendIntent = (Button)findViewById(R.id.btn_id_send_intent);
        mBtnSendIntent.setOnClickListener(mBtnListener);

        mBtnSetTimer = (Button)findViewById(R.id.btn_id_set_timer);
        mBtnSetTimer.setOnClickListener(mBtnListener);

        mBtnAddCalendarEvent = (Button)findViewById(R.id.btn_id_add_calendar_event);
        mBtnAddCalendarEvent.setOnClickListener(mBtnListener);

        mBtnRuntimePermission = (Button) findViewById(R.id.btn_id_rt_permissions);
        mBtnRuntimePermission.setOnClickListener(mBtnListener);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, PER_WRITE_SDCARD)
                == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "permission not granted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PER_WRITE_SDCARD)) {
                Toast.makeText(this, "This is a explanation:" +
                        "Why u app need write sdcard permission", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{PER_WRITE_SDCARD},
                        PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_WRITE);
            } else {
                Log.d(TAG, "try to request permission");
                ActivityCompat.requestPermissions(this,
                        new String[]{PER_WRITE_SDCARD},
                        PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_WRITE);
            }
        } else {
            Log.d(TAG, "permission granted");
            createFile();
        }
    }

    private void createFile() {
        File sdcard = Environment.getExternalStorageDirectory();
        File newFile = new File(sdcard, "/AppDemo.txt");
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
                Toast.makeText(getApplicationContext(), "new AppDemo.txt create.",
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_WRITE: {
                // If request is cancelled, the result arrays are empty
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Log.d(TAG, "grant write permission successfully");
                    createFile();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(TAG, "write permission denied");
                }
                return;
            }
        }
    }

    public void addEvent(String title, String location, Calendar begin, Calendar end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

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
        sendIntent.setType("text/plain"); // "text/plain" MIME type

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

        FeatureInfo[] featureInfo = mPm.getSystemAvailableFeatures();
        Log.d(TAG, "getSystemAvailableFeatures returns " + featureInfo.length + " items:");
        int i = 0;
        for (FeatureInfo fi : featureInfo) {
            //Log.d(TAG, i + ":" + fi);
            i++;
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
