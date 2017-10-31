package ru.savchenko.andrey.blockchain.storage;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.savchenko.andrey.blockchain.entities.MoneyScore;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.BEST;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;
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
        return new USDRepository().getUSDByCalendarOneDayForward(getCalendarByDate(date));
    }

    public static Calendar getCalendarByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
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

    public static String getFormattedStringOfDouble(Double value){
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

    public static int reallyMoneyGetMax() {
        USD lastUSD = new USDRepository().getLastUSD();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        MoneyScore todayMoneyScore = new USDRepository().getMaxToday(Utils.getCalendarByDate(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        MoneyScore yesterdayScore = new USDRepository().getMaxToday(calendar);
        if (lastUSD.getLast() > yesterdayScore.getMax() && lastUSD.getLast().equals(todayMoneyScore.getMax())) {
            //значит сегодняшний максимум превысил вчерашний нужно продавать биткоин
            return SELL_OPERATION;
        }
        if (lastUSD.getLast() < yesterdayScore.getMin() && lastUSD.getLast().equals(todayMoneyScore.getMin())) {
            //значит сегодняшний минимкм упал ниже вчерашнего нужно покупать биткоин
            return BUY_OPERATION;
        }
        return 0;
    }
//сравнить предыдущий мин макс за день
    public static int previousMaxOrMin(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        MoneyScore todayMoneyScore = new USDRepository().getMaxToday(calendar);
        USD preLastUSD = new USDRepository().getPreLastUSD(calendar);
        Log.i(TAG, "previousMaxOrMin: " + todayMoneyScore);
        Log.i(TAG, "previousMaxOrMin: preLastUSD " + preLastUSD.getLast());
        if(todayMoneyScore!=null){
            if(todayMoneyScore.getMax().equals(preLastUSD.getLast())){
                Log.i(TAG, "previousMaxOrMin: значит цена с максимума пошла на спад, надо продавать биткоин");
                //значит цена с максимума пошла на спад, надо продавать биткоин
                return SELL_OPERATION;
            }else if(todayMoneyScore.getMin().equals(preLastUSD.getLast())){
                Log.i(TAG, "previousMaxOrMin: значит цена с минимума пошла на повышение надо покупать биткоин");
                //значит цена с минимума пошла на повышение надо покупать биткоин
                return BUY_OPERATION;
            }
        }
        return 0;
    }
//сравнить с макс или мин за 4 часа
    public static int previousMaxOrMinFourHours(){
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
