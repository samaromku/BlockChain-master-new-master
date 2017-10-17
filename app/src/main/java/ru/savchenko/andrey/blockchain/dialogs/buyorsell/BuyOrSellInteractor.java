package ru.savchenko.andrey.blockchain.dialogs.buyorsell;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.base.BaseRepository;
import ru.savchenko.andrey.blockchain.di.BuyOrSellScope;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;

import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;

/**
 * Created by Andrey on 17.10.2017.
 */
@Module
public class BuyOrSellInteractor {
    private MoneyCount moneyCount = new BaseRepository<>(MoneyCount.class).getItem();

    @BuyOrSellScope
    @Provides
    BuyOrSellInteractor interactor(){
        return this;
    }

    public Observable<MoneyCount> sellUSDInteractor(Double usdSize, Double btcSize){
        USD lastUsd = new USDRepository().getLastUSD();
        lastUsd.setBuyOrSell(SELL_OPERATION);
        if(usdSize >0) {
            Double btcValue = btcSize + usdSize / lastUsd.getBuy();
            Double restUsd = moneyCount.getUsdCount() - usdSize;

            moneyCount.setUsdCount(restUsd);
            moneyCount.setBitCoinCount(btcValue);
            return Observable.fromCallable(() -> moneyCount);
        }else {
            return Observable.empty();
        }
    }

    public Observable<MoneyCount> sellBTCInteractor(Double usdSize, Double btcSize){
        USD lastUsd = new USDRepository().getLastUSD();
        lastUsd.setBuyOrSell(BUY_OPERATION);
        if(btcSize >0) {
            Double usdValue = usdSize + btcSize * lastUsd.getSell();
            Double restBtc = moneyCount.getBitCoinCount() - btcSize;

            moneyCount.setUsdCount(usdValue);
            moneyCount.setBitCoinCount(restBtc);
            return Observable.fromCallable(() -> moneyCount);
        }else {
            return Observable.empty();
        }
    }
}
