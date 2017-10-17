package ru.savchenko.andrey.blockchain.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Andrey on 17.10.2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface BuyOrSellScope {
}
