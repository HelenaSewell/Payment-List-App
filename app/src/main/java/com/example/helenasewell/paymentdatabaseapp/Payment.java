package com.example.helenasewell.paymentdatabaseapp;

import android.util.Log;

/**
 * Created by Helena Sewell on 23/07/2014.
 * The Payment object and its methods.
 */
public class Payment {
    private long id;
    private String date;
    private int gross;
    private int net;

    public long getID(){
        Log.d("payment", Long.toString(id));
        return id;
        }
    public void setId(long id){
        this.id=id;
        Log.d("payment", Long.toString(id));
    }

    public String getDate(){
        Log.d("payment", date);
        return date;
    }
    public void setDate(String date){
        this.date=date;
        Log.d("payment", date);
    }

    public int getGross(){
        Log.d("payment", Integer.toString(gross));
        return gross;
    }
    public void setGross(int gross){
        this.gross=gross;
        Log.d("payment", Integer.toString(gross));
    }

    public int getNet(){
        Log.d("payment", Integer.toString(gross));
        return net;
    }
    public void setNet(int net){
        this.net=net;
        Log.d("payment", Integer.toString(gross));
    }
}
