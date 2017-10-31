package ru.savchenko.andrey.blockchain.services.exchange;

import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;

/**
 * Created by Andrey on 30.10.2017.
 */

public class ExchangeRepository implements IExchangeRepository{
    @Override
    public Observable<MoneyCount> writeInDBWithoutChange() {
        return null;
    }

    @Override
    public Observable<MoneyCount> sellBTCInteractor(Double usdSize, Double btcSize) {
        return null;
    }

    @Override
    public Observable<MoneyCount> sellUSDInteractor(Double usdSize, Double btcSize) {
        return null;
    }
}
