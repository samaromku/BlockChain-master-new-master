package ru.savchenko.andrey.blockchain.services.exchange;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;
import java.util.List;

import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.IChecker;
import ru.savchenko.andrey.blockchain.interfaces.IUSDRepository;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Checker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Andrey on 30.10.2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class ExchangePresenterTest {
    ExchangePresenter presenter;
    ExchangeView view;
    MoneyCount moneyCount = new MoneyCount(1, 1000.0, 0.01);


    @Before
    public void setup(){
        view = mock(ExchangeView.class);
        presenter = new ExchangePresenter(view, new ExchangeInteractor(new BaseRepository<>(MoneyCount.class), new USDRepository(), new Checker()));
//        presenter.exchangeInteractor.setBaseRepository(new TestBaseRepository());
//        presenter.exchangeInteractor.setChecker(new TestChecker());
//        presenter.exchangeInteractor.setIusdRepository(new TestUSDRepository());
    }

    @Test
    public void sellUSD() throws Exception {
        PowerMockito.mockStatic(Log.class);
        presenter.sellUSD();
        verify(view).showNotify(moneyCount);
    }

    private class TestChecker implements IChecker{
        @Override
        public int previousMaxOrMinFourHours() {
            return 0;
        }
    }

    private class TestUSDRepository implements IUSDRepository{
        @Override
        public void writeIdDb(USD usd) {

        }

        @Override
        public void setBuyOrSell(USD lastUSD, int operation) {

        }

        @Override
        public int writeIdDbReturnInteger(USD usd) {
            return 0;
        }

        @Override
        public USD getLastUSD() {
            return new USD(594, 6001.08, 6001.63, 6001.08, 6000.52, "$", new Date(1508722909241L), 0, null);
        }
    }

    private class TestBaseRepository implements IBaseRepository<MoneyCount>{
        @Override
        public void addItem(MoneyCount item) {

        }

        @Override
        public void addAll(List<MoneyCount> items) {

        }

        @Override
        public void removeItem(MoneyCount item) {

        }

        @Override
        public MoneyCount getItem() {
            return moneyCount;
        }

        @Override
        public List<MoneyCount> getAll() {
            return null;
        }

        @Override
        public MoneyCount getLast() {
            return null;
        }

        @Override
        public MoneyCount getItemById(int id) {
            return null;
        }

        @Override
        public int getMaxIdPlusOne() {
            return 0;
        }
    }
}