package ru.savchenko.andrey.blockchain.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;

/**
 * Created by Andrey on 21.10.2017.
 */

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        Intent restartService = new Intent("RestartService");
        sendBroadcast(restartService);
    }
}
