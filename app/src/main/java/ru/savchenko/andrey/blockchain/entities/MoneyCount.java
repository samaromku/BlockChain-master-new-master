package ru.savchenko.andrey.blockchain.entities;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrey on 15.10.2017.
 */

public class MoneyCount extends RealmObject{
    @PrimaryKey
    private int id;
    private Double usdCount;
    private Double bitCoinCount;
    private int buyOrSell;

    public int isBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(int buyOrSell) {
        Realm.getDefaultInstance().executeTransaction(realm -> this.buyOrSell = buyOrSell);
        Realm.getDefaultInstance().close();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getUsdCount() {
        return usdCount;
    }

    public void setUsdCount(Double usdCount) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            this.usdCount = usdCount;
        });
        Realm.getDefaultInstance().close();
    }

    public Double getBitCoinCount() {
        return bitCoinCount;
    }

    public void setBitCoinCount(Double bitCoinCount) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            this.bitCoinCount = bitCoinCount;
        });
        Realm.getDefaultInstance().close();
    }

    @Override
    public String toString() {
        return "MoneyCount{" +
                "id=" + id +
                ", usdCount=" + usdCount +
                ", bitCoinCount=" + bitCoinCount +
                ", buyOrSell=" + buyOrSell +
                '}';
    }

    public MoneyCount() {
    }

    public MoneyCount(int id, Double usdCount, Double bitCoinCount) {
        this.id = id;
        this.usdCount = usdCount;
        this.bitCoinCount = bitCoinCount;
    }
}
