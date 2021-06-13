package org.samir.simplecounter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String COUNTER_DB = "counter_db";
    private static final String COUNTERS = "counters";
    private static SharedPreferences sharedPreferences;
    private static Utils instance;

    private Utils(Context context){
        sharedPreferences = context.getSharedPreferences(COUNTER_DB,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        if(getAllCounters() == null){
            editor.putString(COUNTERS,gson.toJson(new ArrayList<CounterItem>()));
            editor.commit();
        }
    }

    public static Utils getInstance(Context context){
        if(instance == null){
            instance = new Utils(context);
        }
        return  instance;
    }

    public static ArrayList<CounterItem> getAllCounters(){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<CounterItem>>(){}.getType();
        ArrayList<CounterItem> allCounters = new ArrayList<>();
        allCounters = gson.fromJson(sharedPreferences.getString(COUNTERS,null),type);
        return allCounters;
    }

    public static boolean addCounter(String counterName){
        ArrayList<CounterItem> allCounters = getAllCounters();
        if(allCounters != null){
            CounterItem counterItem = new CounterItem(counterName,0);
            if(allCounters.add(counterItem)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(COUNTERS);
                editor.putString(COUNTERS,gson.toJson(allCounters));
                editor.commit();
                return true;
            }
            return false;
        }
        return false;
    }

    public void incrementCount(CounterItem counterItem,CounterItemAdapter adapter){
        ArrayList<CounterItem> newCounterItems = new ArrayList<>();
        ArrayList<CounterItem> counterItems = getAllCounters();
        for(CounterItem c: counterItems){
            if(c.getId() == counterItem.getId()){
                c.setCount(c.getCount() + 1);
                newCounterItems.add(c);
            }else{
                newCounterItems.add(c);
            }
        }
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(COUNTERS);
        editor.putString(COUNTERS,gson.toJson(newCounterItems));
        editor.commit();
        adapter.setCounterItems(newCounterItems);
    }

    public void decrementCount(CounterItem counterItem,CounterItemAdapter adapter){
        ArrayList<CounterItem> newCounterItems = new ArrayList<>();
        ArrayList<CounterItem> counterItems = getAllCounters();
        for(CounterItem c: counterItems){
            if(c.getId() == counterItem.getId()){
                c.setCount(c.getCount() - 1);
                newCounterItems.add(c);
            }else{
                newCounterItems.add(c);
            }
        }
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(COUNTERS);
        editor.putString(COUNTERS,gson.toJson(newCounterItems));
        editor.commit();
        adapter.setCounterItems(newCounterItems);
    }

    public void deleteCounter(CounterItem counterItem,CounterItemAdapter adapter){
        ArrayList<CounterItem> newCounterItems = new ArrayList<>();
        ArrayList<CounterItem> counterItems = getAllCounters();
        for(CounterItem c: counterItems){
            if(c.getId() != counterItem.getId()){
                newCounterItems.add(c);
            }
        }
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(COUNTERS);
        editor.putString(COUNTERS,gson.toJson(newCounterItems));
        editor.commit();
        adapter.setCounterItems(newCounterItems);
    }

    public static int getId(){
        ArrayList<CounterItem> counterItems = getAllCounters();
        if(counterItems != null){
            if(counterItems.size() > 0) {
                CounterItem lastItem = counterItems.get(counterItems.size() - 1);
                return lastItem.getId() + 1;
            }
        }
        return 1;
    }
}
