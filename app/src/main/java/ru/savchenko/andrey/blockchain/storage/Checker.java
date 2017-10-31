package ru.savchenko.andrey.blockchain.storage;

import android.util.Log;

import ru.savchenko.andrey.blockchain.entities.MoneyScore;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.IChecker;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;

/**
 * Created by Andrey on 30.10.2017.
 */

public class Checker implements IChecker {

    public int previousMaxOrMinFourHours(){
        MoneyScore todayMoneyScore = new USDRepository().getMaxFourHours();
        USD lastUSD = new USDRepository().getLastUSD();
        Log.i(TAG, "previousMaxOrMin: " + todayMoneyScore);
        Log.i(TAG, "previousMaxOrMin: preLastUSD " + lastUSD.getLast());
        if(todayMoneyScore!=null){
            if(todayMoneyScore.getMax().equals(lastUSD.getLast())){
                Log.i(TAG, "previousMaxOrMin: значит цена с максимума пошла на спад, надо продавать биткоин");
                //значит цена с максимума пошла на спад, надо продавать биткоин
                return SELL_OPERATION;
            }else if(todayMoneyScore.getMin().equals(lastUSD.getLast())){
                Log.i(TAG, "previousMaxOrMin: значит цена с минимума пошла на повышение надо покупать биткоин");
                //значит цена с минимума пошла на повышение надо покупать биткоин
                return BUY_OPERATION;
            }
        }
        return 0;
    }

}
