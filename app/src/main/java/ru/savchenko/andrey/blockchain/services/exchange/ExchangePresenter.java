package ru.savchenko.andrey.blockchain.services.exchange;

import javax.inject.Inject;

import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.dialogs.buyorsell.BuyOrSellInteractor;

/**
 * Created by Andrey on 17.10.2017.
 */

public class ExchangePresenter {
    private ExchangeView view;
    @Inject
    BuyOrSellInteractor buyInteractor;
    private ExchangeInteractor exchangeInteractor;

    public ExchangePresenter(ExchangeView view, ExchangeInteractor exchangeInteractor) {
        this.view = view;
        this.exchangeInteractor = exchangeInteractor;
        ComponentManager.getAppComponent().inject(this);
    }

    void sellUSD() {
        exchangeInteractor.buyOrSellMethod()
                .subscribe(moneyCount -> {
                    view.showNotify(moneyCount);
                });
    }
}
