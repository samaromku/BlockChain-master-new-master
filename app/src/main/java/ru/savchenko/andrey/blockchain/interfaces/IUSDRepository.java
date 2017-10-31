package ru.savchenko.andrey.blockchain.interfaces;

import ru.savchenko.andrey.blockchain.entities.USD;

/**
 * Created by Andrey on 30.10.2017.
 */

public interface IUSDRepository {
    void writeIdDb(USD usd);
    int writeIdDbReturnInteger(USD usd);
    USD getLastUSD();
    void setBuyOrSell(USD lastUSD, int operation);
}
