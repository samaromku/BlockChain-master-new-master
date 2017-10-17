package ru.savchenko.andrey.blockchain.services.exchange;

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

/**
 * Created by Andrey on 17.10.2017.
 */
@Module
public class ExchangeInteractor {
    private MoneyCount moneyCount = new BaseRepository<>(MoneyCount.class).getItem();
    @Inject
    BuyOrSellInteractor interactor;


    @Provides
    ExchangeInteractor interactor(){
        ComponentManager.getBuyOrSellComponent().inject(this);
        return this;
    }

    Observable<MoneyCount> buyOrSellMethod(){
        if(buyOrSell()){
            moneyCount.setBuyOrSell(true);
            return interactor.sellUSDInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
        }else {
            moneyCount.setBuyOrSell(false);
            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
        }
    }

    private boolean buyOrSell() {
        List<USD> lastValues = new USDRepository().getLastThreeValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        if ((firstFromLast > secondFromLast) && (secondFromLast > thirdFromLast)){
            return false;
        }else {
            return true;
        }
    }

}
