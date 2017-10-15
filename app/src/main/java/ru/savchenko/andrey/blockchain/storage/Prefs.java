package ru.savchenko.andrey.blockchain.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andrey on 15.10.2017.
 */

public class Prefs {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    public static final String INTERVAL = "interval";
    public static final String COUNT = "count";

    public static void init(Context context){
        sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static void writeInterval(int interval){
        editor.putInt(INTERVAL, interval);
        editor.apply();
    }

    public static int getInterval(){
        return sp.getInt(INTERVAL, 0);
    }
}
