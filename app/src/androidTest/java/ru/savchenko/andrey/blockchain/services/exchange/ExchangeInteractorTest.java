package ru.savchenko.andrey.blockchain.services.exchange;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Checker;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Andrey on 31.10.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ExchangeInteractorTest {
    private ExchangeInteractor interactor;

    @Before
    public void setup(){
        interactor = new ExchangeInteractor(new BaseRepository<>(MoneyCount.class), new USDRepository(), new Checker());
    }

    @Test
    public void interactor() throws Exception {
    }

    @Test
    public void buyOrSellMethod() throws Exception {
        interactor.buyOrSellMethod()
                .subscribe(moneyCount -> assertEquals(moneyCount, (new MoneyCount())));
    }

    @Test
    public void writeInDBWithoutChange() throws Exception {
        interactor.writeInDBWithoutChange().subscribe(moneyCount -> assertEquals(moneyCount, new MoneyCount()));
    }

}