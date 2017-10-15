package ru.savchenko.andrey.blockchain;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.realm.Realm;
import ru.savchenko.andrey.blockchain.network.RequestManager;
import ru.savchenko.andrey.blockchain.services.UpdateExchangeService;
import ru.savchenko.andrey.blockchain.storage.Prefs;

/**
 * Created by Andrey on 12.10.2017.
 */

public class App extends Application {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.init();
        Realm.init(this);
        Prefs.init(this);
        startService(UpdateExchangeService.newIntent(this));
    }
}
