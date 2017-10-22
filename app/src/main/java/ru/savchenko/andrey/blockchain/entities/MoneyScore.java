package ru.savchenko.andrey.blockchain.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrey on 22.10.2017.
 */

public class MoneyScore extends RealmObject{
    @PrimaryKey
    private int id;
    private Double max;
    private Double min;

    @Override
    public String toString() {
        return "MoneyScore{" +
                "id=" + id +
                ", max=" + max +
                ", min=" + min +
                '}';
    }

    public MoneyScore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public MoneyScore(int id, Double max, Double min) {
        this.id = id;
        this.max = max;
        this.min = min;
    }
}
