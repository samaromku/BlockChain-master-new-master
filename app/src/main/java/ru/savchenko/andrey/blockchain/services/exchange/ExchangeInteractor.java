package ru.savchenko.andrey.blockchain.services.exchange;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.base.BaseRepository;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.dialogs.buyorsell.BuyOrSellInteractor;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Utils;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;

/**
 * Created by Andrey on 17.10.2017.
 */
@Module
public class ExchangeInteractor {
    private MoneyCount moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
    @Inject
    BuyOrSellInteractor interactor;


    @Provides
    ExchangeInteractor interactor() {
        ComponentManager.getBuyOrSellComponent().inject(this);
        return this;
    }

    Observable<MoneyCount> buyOrSellMethod() {
        int buyOrSell = otherValues();
//        if(Utils.saver()==-1){
//            moneyCount.setBuyOrSell(true);
//            return interactor.sellUSDInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
//        }else if(Utils.saver()==1){
//            moneyCount.setBuyOrSell(false);
//            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
//        }
        int trueSellOrBuy = Utils.reallyMoneyGetMax();
        if(trueSellOrBuy == -1){
            moneyCount.setBuyOrSell(true);
            return interactor.sellUSDInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
        }else if(trueSellOrBuy==1){
            moneyCount.setBuyOrSell(false);
            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
        }


//        if (buyOrSell == -1) {
//            moneyCount.setBuyOrSell(true);
//            return interactor.sellUSDInteractor(moneyCount.getUsdCount() * 0.5, moneyCount.getBitCoinCount());
//        } else if (buyOrSell == 1) {
//            moneyCount.setBuyOrSell(false);
//            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount() * 0.5);
//        }
        return interactor.writeInDBWithoutChange();
    }

    private int buyOrSell() {
        List<USD> lastValues = new USDRepository().getLastFiveValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        Double fourthFromLast = lastValues.get(3).getLast();
        Log.i(TAG, "buyOrSell: " + firstFromLast + " " + secondFromLast + " " + thirdFromLast + " " + fourthFromLast);

        if ((firstFromLast > secondFromLast) && (secondFromLast > thirdFromLast) && (fourthFromLast > thirdFromLast)) {
            Log.i(TAG, "first condition");
            return -1;
        } else if ((fourthFromLast > thirdFromLast) && (thirdFromLast > secondFromLast) && (firstFromLast > secondFromLast)) {
            Log.i(TAG, "second condition");
            return 1;
        }
        return 0;
    }

    //20 $ в день с 1000$ когда как....(
    private int otherValues() {
        List<USD> lastValues = new USDRepository().getLastFiveValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        Double fourthFromLast = lastValues.get(3).getLast();
        Log.i(TAG, "buyOrSell: " + firstFromLast + " " + secondFromLast + " " + thirdFromLast + " " + fourthFromLast);

        if ((fourthFromLast > thirdFromLast) && (thirdFromLast > secondFromLast) && (firstFromLast > secondFromLast)) {
            Log.i(TAG, "first condition");
            return 1;
        } else if ((fourthFromLast < thirdFromLast) && (thirdFromLast < secondFromLast) && (firstFromLast < secondFromLast)) {
            Log.i(TAG, "second condition");
            return -1;
        }
        return 0;
    }
}
