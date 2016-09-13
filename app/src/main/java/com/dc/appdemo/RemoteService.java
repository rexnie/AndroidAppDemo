package com.dc.appdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by niedaocai on 9/8/16.
 */
public class RemoteService extends Service {
    private static final String TAG = "RemoteService";
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG, "in Stub.basicTypes:");
            Log.d(TAG, "aLong=" + aLong + ",aDouble=" + aDouble + ",aString=" + aString);

            aLong *= 10;
            aDouble *= 10.0;
            aString += "+ service";
        }

        @Override
        public int getPid() throws RemoteException {
            Log.d(TAG, "in Stub.getPid");
            return Process.myPid();
        }

        @Override
        public void doWorkMoreTime(int timeInMs) throws RemoteException {
            Log.d(TAG, "doWorkMoreTime in servie is called");
            try {
                Thread.sleep(timeInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "doWorkMoreTime");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

}
