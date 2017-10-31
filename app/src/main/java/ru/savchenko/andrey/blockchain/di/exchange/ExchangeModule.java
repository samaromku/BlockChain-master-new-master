package ru.savchenko.andrey.blockchain.di.exchange;

import dagger.Module;
import dagger.Provides;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.interfaces.IChecker;
import ru.savchenko.andrey.blockchain.interfaces.IUSDRepository;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangeInteractor;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangePresenter;
import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;
import ru.savchenko.andrey.blockchain.storage.Checker;

/**
 * Created by Andrey on 30.10.2017.
 */
@Module
public class ExchangeModule {
    private UpdateExchangeService service;

    public ExchangeModule(UpdateExchangeService service) {
        this.service = service;
    }

    @ExchangeScope
    @Provides
    ExchangePresenter presenter(ExchangeInteractor exchangeInteractor){
        return new ExchangePresenter(service, exchangeInteractor);
    }

    @ExchangeScope
    @Provides
    IBaseRepository<MoneyCount> baseRepository(){
        return new BaseRepository<>(MoneyCount.class);
    }

    @ExchangeScope
    @Provides
    IUSDRepository iusdRepository(){
        return new USDRepository();
    }

    @ExchangeScope
    @Provides
    IChecker checker(){
        return new Checker();
    }

    @ExchangeScope
    @Provides
    ExchangeInteractor exchangeInteractor(IBaseRepository<MoneyCount> baseRepository, IUSDRepository iusdRepository, IChecker checker){
        return new ExchangeInteractor(baseRepository, iusdRepository, checker);
    }


}
