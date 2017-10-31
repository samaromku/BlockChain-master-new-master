package ru.savchenko.andrey.blockchain.dialogs.buyorsell;

import android.util.Log;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.interfaces.IUSDRepository;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.di.BuyOrSellScope;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Utils;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.NO_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;

/**
 * Created by Andrey on 17.10.2017.
 */
@Module
public class BuyOrSellInteractor {
    private MoneyCount moneyCount;

    @BuyOrSellScope
    @Provides
    BuyOrSellInteractor interactor() {
        return this;
    }
    private IUSDRepository iusdRepository;

    public BuyOrSellInteractor() {
        this.iusdRepository = new USDRepository();
    }

    Observable<String> setUSDRest() {
        moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
        Log.i(TAG, "setUSDRest: " + moneyCount);
        Double usdRest = moneyCount.getUsdCount();
        Double btcRest = moneyCount.getBitCoinCount();
        USD lastUsd = new USDRepository().getLastUSD();
        Double rest = usdRest + btcRest * lastUsd.getSell();
        return Observable.fromCallable(() -> Utils.getFormattedStringOfDouble(rest));
    }

    public Observable<MoneyCount> sellUSDInteractor(Double usdSize, Double btcSize) {
        moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
        USD lastUsd = iusdRepository.getLastUSD();
        iusdRepository.setBuyOrSell(lastUsd, SELL_OPERATION);
        if (usdSize > 0) {
            lastUsd.setBuyOrSelled(usdSize);
            Double btcValue = btcSize + usdSize / lastUsd.getBuy();
            Double restUsd = moneyCount.getUsdCount() - usdSize;

            moneyCount.setUsdCount(restUsd);
            moneyCount.setBitCoinCount(btcValue);
            return Observable.fromCallable(() -> moneyCount);
        } else {
            return Observable.empty();
        }
    }

    public Observable<MoneyCount> sellBTCInteractor(Double usdSize, Double btcSize) {
        moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
        USD lastUsd = iusdRepository.getLastUSD();
        iusdRepository.setBuyOrSell(lastUsd, BUY_OPERATION);
        if (btcSize > 0) {
            lastUsd.setBuyOrSelled(btcSize*lastUsd.getSell());
            Double usdValue = usdSize + btcSize * lastUsd.getSell();
            Double restBtc = moneyCount.getBitCoinCount() - btcSize;

            moneyCount.setUsdCount(usdValue);
            moneyCount.setBitCoinCount(restBtc);
            return Observable.fromCallable(() -> moneyCount);
        } else {
            return Observable.empty();
        }
    }

    public Observable<MoneyCount>writeInDBWithoutChange(){
        moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
        USD lastUsd = iusdRepository.getLastUSD();
        iusdRepository.setBuyOrSell(lastUsd, NO_OPERATION);
        return Observable.fromCallable(() -> moneyCount);
    }
}
