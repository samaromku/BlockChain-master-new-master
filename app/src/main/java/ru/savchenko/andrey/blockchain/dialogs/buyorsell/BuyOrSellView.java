package ru.savchenko.andrey.blockchain.dialogs.buyorsell;

/**
 * Created by Andrey on 17.10.2017.
 */

public interface BuyOrSellView {
    void setTextUSD(String textUSD);

    void setTextBTC(String textBTC);

    void makeToast(String text);

    void refreshAdapter();

    void setMoneyRest(String moneyRest);
}
