package ru.savchenko.andrey.blockchain.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;

/**
 * Created by Andrey on 21.10.2017.
 */

public class RestartService extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(UpdateExchangeService.newIntent(context));
    }
}
