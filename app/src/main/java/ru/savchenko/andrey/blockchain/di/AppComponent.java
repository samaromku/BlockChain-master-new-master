package ru.savchenko.andrey.blockchain.di;

import dagger.Component;
import ru.savchenko.andrey.blockchain.di.exchange.ExchangeModule;
import ru.savchenko.andrey.blockchain.di.exchange.ExchangeSubComponent;
import ru.savchenko.andrey.blockchain.dialogs.buyorsell.BuyOrSellInteractor;
import ru.savchenko.andrey.blockchain.dialogs.buyorsell.BuyOrSellPresenter;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangeInteractor;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangePresenter;

/**
 * Created by Andrey on 25.09.2017.
 */
@BuyOrSellScope
@Component(modules = {BuyOrSellInteractor.class})
public interface AppComponent {
    void inject(BuyOrSellPresenter presenter);

    void inject(ExchangeInteractor interactor);

    void inject(ExchangePresenter presenter);

    ExchangeSubComponent exchangeSubComponent(ExchangeModule exchangeModule);
}
