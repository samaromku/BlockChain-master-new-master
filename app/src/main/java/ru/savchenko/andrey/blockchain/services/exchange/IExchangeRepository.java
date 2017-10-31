package ru.savchenko.andrey.blockchain.services.exchange;

import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;

/**
 * Created by Andrey on 30.10.2017.
 */

public interface IExchangeRepository {
    Observable<MoneyCount> writeInDBWithoutChange();
    Observable<MoneyCount> sellBTCInteractor(Double usdSize, Double btcSize);
    Observable<MoneyCount> sellUSDInteractor(Double usdSize, Double btcSize);
}
