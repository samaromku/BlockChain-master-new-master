package ru.savchenko.andrey.blockchain.services.exchange;

import ru.savchenko.andrey.blockchain.entities.MoneyCount;

/**
 * Created by Andrey on 17.10.2017.
 */

public interface ExchangeView {
    void showToast(String text);

    void showNotify(MoneyCount moneyCount);
}
