package ru.savchenko.andrey.blockchain.repositories;

import io.realm.Realm;

/**
 * Created by Andrey on 15.10.2017.
 */

public class CountRepository {
    private Realm realmInstance() {
        return Realm.getDefaultInstance();
    }


}
