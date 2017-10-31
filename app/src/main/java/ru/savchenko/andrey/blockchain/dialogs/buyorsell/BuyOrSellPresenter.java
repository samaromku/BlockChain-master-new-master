package ru.savchenko.andrey.blockchain.dialogs.buyorsell;

import javax.inject.Inject;

import ru.savchenko.andrey.blockchain.di.ComponentManager;

/**
 * Created by Andrey on 17.10.2017.
 */

public class BuyOrSellPresenter {
    @Inject BuyOrSellInteractor interactor;
    private BuyOrSellView view;

    public BuyOrSellPresenter(BuyOrSellView view) {
        this.view = view;
        ComponentManager.getAppComponent().inject(this);
    }

    public void sellUSD(String usdSize, String btcSize, boolean sellUSD){
        Double usdValue = Double.valueOf(usdSize);
        Double btcValue = Double.valueOf(btcSize);
        if(sellUSD) {
            interactor.sellUSDInteractor(usdValue, btcValue)
                    .switchIfEmpty(observer -> view.makeToast("Недостаточно средств"))
                    .subscribe(moneyCount -> {
                        view.refreshAdapter();
                        view.setTextUSD(String.valueOf(moneyCount.getUsdCount()));
                        view.setTextBTC(String.valueOf(moneyCount.getBitCoinCount()));
                        interactor.setUSDRest()
                                .subscribe(s -> view.setMoneyRest(s));
                    });
        }else {
            interactor.sellBTCInteractor(usdValue, btcValue)
                    .switchIfEmpty(observer -> view.makeToast("Недостаточно средств"))
                    .subscribe(moneyCount -> {
                        view.refreshAdapter();
                        view.setTextUSD(String.valueOf(moneyCount.getUsdCount()));
                        view.setTextBTC(String.valueOf(moneyCount.getBitCoinCount()));
                        interactor.setUSDRest()
                                .subscribe(s -> view.setMoneyRest(s));
                    });
        }
    }

    void setUSDRest(){
        interactor.setUSDRest()
                .subscribe(s -> view.setMoneyRest(s));
    }
}
