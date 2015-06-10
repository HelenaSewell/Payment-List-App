package com.example.helenasewell.paymentdatabaseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Helena Sewell on 23/07/2014.
 * SQL database helper
 * records name of database and columns
 * methods for creation and updating
 */
public class PaymentHistoryHelper extends SQLiteOpenHelper {

    public static final String TABLE_PAYMENT="payment";
    public static final String COLUMN_ID="paymentid";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_GROSS="gross";
    public static final String COLUMN_NET="net";

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="PaymentHistory.db";

    private static final String SQL_CREATE_ENTRIES="create table "
            +TABLE_PAYMENT+"("+COLUMN_ID+" integer primary key autoincrement, "
            +COLUMN_DATE+" text not null, "+COLUMN_GROSS+" text not null, "
            +COLUMN_NET+" text not null);";

    public PaymentHistoryHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PAYMENT);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db,int oldV,int newV){
        onUpgrade(db,oldV,newV);
    }

}
