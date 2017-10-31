package ru.savchenko.andrey.blockchain.repositories;

import java.util.List;

/**
 * Created by Andrey on 30.10.2017.
 */
public interface IBaseRepository<T> {

    void addItem(T item);

    void addAll(List<T> items);

    void removeItem(T item);

    T getItem();

    List<T> getAll();

    T getLast();

    T getItemById(int id);

    int getMaxIdPlusOne();

}
