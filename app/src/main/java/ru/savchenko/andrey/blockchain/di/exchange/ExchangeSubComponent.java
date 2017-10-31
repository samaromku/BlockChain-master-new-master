package ru.savchenko.andrey.blockchain.di.exchange;

import dagger.Subcomponent;
import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;

/**
 * Created by Andrey on 30.10.2017.
 */
@ExchangeScope
@Subcomponent(modules = ExchangeModule.class)
public interface ExchangeSubComponent {
    void inject(UpdateExchangeService service);
}
