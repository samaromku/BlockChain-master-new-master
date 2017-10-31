package ru.savchenko.andrey.blockchain.repositories;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.Sort;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.ID;

/**
 * Created by Andrey on 09.10.2017.
 */
public class BaseRepository<T extends RealmObject> implements IBaseRepository<T>{
    private Class<T> type;

    public BaseRepository(Class<T> type) {
        this.type = type;
    }

    private Realm realmInstance() {
        return Realm.getDefaultInstance();
    }

    public void addItem(T item) {
        Log.i(TAG, "addItem: " + item);
        realmInstance().executeTransaction(realm -> realm.insertOrUpdate(item));
        realmInstance().close();
    }

    public void addAll(List<T> items) {
        realmInstance().executeTransaction(realm -> realm.insertOrUpdate(items));
        realmInstance().close();
    }

    public void removeItem(T item){
        realmInstance().executeTransaction(realm -> item.deleteFromRealm());
        realmInstance().close();
    }

    public T getItem() {
        T t = realmInstance().where(type).findFirst();
        Log.i(TAG, "getItem: " +t);
        realmInstance().close();
        return t;
    }

    public List<T> getAll() {
        List<T> tList = realmInstance().where(type).findAll();
        realmInstance().close();
        return tList;
    }

    public T getLast(){
        List<T>tList = realmInstance().where(type).findAllSorted(ID, Sort.DESCENDING);
        T t = null;
        if(!tList.isEmpty()){
            t = tList.get(0);
        }
        realmInstance().close();
        return t;
    }

    public T getItemById(int id) {
        T t = realmInstance().where(type).equalTo(ID, id).findFirst();
        realmInstance().close();
        return t;
    }

    public int getMaxIdPlusOne() {
        int id = 0;
        if(realmInstance().where(type).max(ID)!=null) {
            id = realmInstance().where(type).max(ID).intValue();
            realmInstance().close();
        }
        return id + 1;
    }
}