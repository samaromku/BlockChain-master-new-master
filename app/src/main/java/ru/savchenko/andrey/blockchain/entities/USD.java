
package ru.savchenko.andrey.blockchain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.annotation.Generated;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class USD extends RealmObject {
    @PrimaryKey
    private int id;
    @SerializedName("15m")
    private Double m5m;
    @SerializedName("buy")
    private Double mBuy;
    @SerializedName("last")
    private Double mLast;
    @SerializedName("sell")
    private Double mSell;
    @SerializedName("symbol")
    private String mSymbol;
    private Date date;
    private int buyOrSell;
    private Double buyOrSelled;

    public Double getBuyOrSelled() {
        return buyOrSelled;
    }

    public void setBuyOrSelled(Double buyOrSelled) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            this.buyOrSelled = buyOrSelled;
        });
        Realm.getDefaultInstance().close();
    }

    public int getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(int buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double get5m() {
        return m5m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getBuy() {
        return mBuy;
    }

    public void setBuy(Double buy) {
        mBuy = buy;
    }

    public Double getLast() {
        return mLast;
    }

    public void setLast(Double last) {
        mLast = last;
    }

    public Double getSell() {
        return mSell;
    }

    public void setSell(Double sell) {
        mSell = sell;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String symbol) {
        mSymbol = symbol;
    }


    @Override
    public String toString() {
        return "USD{" +
                "id=" + id +
                ", m5m=" + m5m +
                ", mBuy=" + mBuy +
                ", mLast=" + mLast +
                ", mSell=" + mSell +
                ", mSymbol='" + mSymbol + '\'' +
                ", date=" + date +
                ", buyOrSell=" + buyOrSell +
                ", buyOrSelled=" + buyOrSelled +
                '}';
    }

    public String addIntList() {
        return "list.add(new USD("
                + id + ", "
                + m5m + ", "
                + mBuy + ", "
                + mLast + ", "
                + mSell + ", "
                + "\"" + mSymbol + "\"" +", "
                + "new Date(" + date.getTime() + "L)," +
                + buyOrSell + ", "
                + buyOrSelled +
                "));";
    }

    public USD() {
    }

    public USD(int id, Double m5m, Double mBuy, Double mLast, Double mSell, String mSymbol, Date date) {
        this.id = id;
        this.m5m = m5m;
        this.mBuy = mBuy;
        this.mLast = mLast;
        this.mSell = mSell;
        this.mSymbol = mSymbol;
        this.date = date;
    }

    public USD(int id, Double m5m, Double mBuy, Double mLast, Double mSell, String mSymbol, Date date, int buyOrSell, Double buyOrSelled) {
        this.id = id;
        this.m5m = m5m;
        this.mBuy = mBuy;
        this.mLast = mLast;
        this.mSell = mSell;
        this.mSymbol = mSymbol;
        this.date = date;
        this.buyOrSell = buyOrSell;
        this.buyOrSelled = buyOrSelled;
    }
}
