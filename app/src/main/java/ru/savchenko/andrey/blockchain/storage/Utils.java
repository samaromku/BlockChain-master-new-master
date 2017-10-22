package ru.savchenko.andrey.blockchain.storage;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.BEST;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL;
import static ru.savchenko.andrey.blockchain.storage.Const.TERRIBLE;
import static ru.savchenko.andrey.blockchain.storage.Const.WAIT;
import static ru.savchenko.andrey.blockchain.storage.Const.WORST;

/**
 * Created by savchenko on 14.10.17.
 */

public class Utils {

    private static double getMiddleValue(Date usdDate){
        List<Double> doublesUSD = new ArrayList<>();
        for(USD usd: getUSDListByDate(usdDate)){
            doublesUSD.add(usd.getLast());
        }
        double allValues = 0;
        for (int i = 0; i < doublesUSD.size(); i++) {
            allValues = allValues + doublesUSD.get(i);
        }
        return allValues/doublesUSD.size();
    }

    public static List<USD>getUSDListByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        Date start = calendar.getTime();
//        calendar.set(Calendar.DAY_OF_MONTH, day + 1);
//        Date end = calendar.getTime();

        return new USDRepository().getUSDByCalendarOneDayForward(calendar);
    }

    public static int getProfit(USD usd){
        if(usd.getLast() >=getMiddleValue(usd.getDate())+10){
            return 10;
        }if(usd.getLast() >=getMiddleValue(usd.getDate())+5){
            return 5;
        }if(usd.getLast() >=getMiddleValue(usd.getDate())){
            return 0;
        }if(usd.getLast() <=getMiddleValue(usd.getDate())){
            return -5;
        }if(usd.getLast() <=getMiddleValue(usd.getDate())-10){
            return -10;
        }
        return -20;
    }

    public static String getBestAndWorstString(USD usd){
        if(Utils.getProfit(usd)==BEST){
            return SELL;
        }else if(Utils.getProfit(usd)==WORST || Utils.getProfit(usd)==TERRIBLE){
            return BUY;
        }
        return WAIT;
    }

    public static int otherValues(){
        List<USD> lastValues = new USDRepository().getLastFiveValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        Double fourthFromLast = lastValues.get(3).getLast();
        Log.i(TAG, "buyOrSell: " + firstFromLast + " " + secondFromLast + " " + thirdFromLast + " " + fourthFromLast);

        if((fourthFromLast>thirdFromLast) && (thirdFromLast>secondFromLast) && (firstFromLast>secondFromLast)) {
            Log.i(TAG, "first condition");
            return 1;
        }
        else if((fourthFromLast<thirdFromLast)&& (thirdFromLast<secondFromLast) && (firstFromLast<secondFromLast)) {
            Log.i(TAG, "second condition");
            return -1;
        }
        return 0;
    }

    public static String setBuyOrSold(Double value){
        if(value!=null) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(value);
        }else return "";
    }

    //предохранитель, если цена вдруг стала резко лезть вверх или вниз
    public static int saver() {
        List<USD> lastValues = new USDRepository().getLastFiveValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        Double fourthFromLast = lastValues.get(3).getLast();
        Double fiveFromLast = lastValues.get(4).getLast();
        //\
        //\\
        //\\\
        //\\\\
        //слишком упал вниз
        if (firstFromLast > secondFromLast &&
                secondFromLast > thirdFromLast &&
                thirdFromLast > fourthFromLast &&
                fourthFromLast > fiveFromLast) {
            return 1;
            //\\\\
            //\\\
            //\\
            //\
            //слишком пошел вверх
        } else if (firstFromLast < secondFromLast &&
                secondFromLast < thirdFromLast &&
                thirdFromLast < fourthFromLast &&
                fourthFromLast < fiveFromLast) {
            return -1;
        }else return 0;
    }
}
