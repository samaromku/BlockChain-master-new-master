package ru.savchenko.andrey.blockchain.services.exchange;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.savchenko.andrey.blockchain.R;
import ru.savchenko.andrey.blockchain.activities.MainActivity;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.network.RequestManager;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Prefs;

import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.USD_ID;


/**
 * Created by Andrey on 12.10.2017.
 */

public class UpdateExchangeService extends IntentService implements ExchangeView {
    public static final String TAG = "UpdateExchangeService";
    @Inject ExchangePresenter presenter;

    public UpdateExchangeService() {
        super(TAG);
        ComponentManager.getExchangeSubComponent(this).inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(isOn){
            am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),  getInterval() * 60000, pi);
        }else{
            am.cancel(pi);
            pi.cancel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Intent newIntent(Context context) {
        return new Intent(context, UpdateExchangeService.class)
                .addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        Intent restartService = new Intent("UpdateExchangeService");
        sendBroadcast(restartService);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: ");
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    private static int getInterval() {
        int interval = Prefs.getInterval();
        if (interval == 0) {
            return 15;
        } else {
            return interval;
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startRxInterval(){
        RequestManager.getRetrofitService().getExchange()
//        Observable.interval(10, TimeUnit.SECONDS)
//                .flatMap(aLong -> RequestManager.getRetrofitService().getExchange())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(exchange -> {
                    Log.i(TAG, "onStartCommand: " + exchange.getUSD() );
                    int usdId = new USDRepository().writeIdDbReturnInteger(exchange.getUSD());
                    if(usdId!=0) {
//                        sendNotify(exchange.getUSD(), usdId);
                        presenter.sellUSD();
                    }
                }, Throwable::printStackTrace);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: " + " date " + new SimpleDateFormat("hh-mm-ss").format(new Date()));
//        if(count==0) {
        if(checkThreeMinutesPassed()) {
            startRxInterval();
        }
//        }
//        count = count + 1;
        return Service.START_NOT_STICKY;
    }

    private boolean checkThreeMinutesPassed(){
        USD last = new BaseRepository<>(USD.class).getLast();
        if(last!=null) {
            Date lastDate = last.getDate();
            Date now = new Date();
            long diffMs = now.getTime() - lastDate.getTime();
            long diffSec = diffMs / 1000;
            long min = diffSec / 60;
            return min > 3;
        }return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void showNotify(MoneyCount moneyCount) {
        Intent intent = new Intent(this, MainActivity.class).putExtra(USD_ID, 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String title = "";
//        int saver = Utils.previousMaxOrMinFourHours();
        if (moneyCount.isBuyOrSell() == SELL_OPERATION) {
            title = "Переломный момент, покупаем доллары";
        } else if (moneyCount.isBuyOrSell() == BUY_OPERATION) {
            title = "Переломный момент, продаем доллары";
        }

        String text = "Закупочная " + new USDRepository().getLastUSD().getBuy();

        if(!TextUtils.isEmpty(title)) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    //.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setSmallIcon(R.drawable.ic_monetization)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000})
                    .setLights(Color.WHITE, 3000, 3000)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        Log.i(TAG, "startForegroundService: ");
        return super.startForegroundService(service);
    }
}
