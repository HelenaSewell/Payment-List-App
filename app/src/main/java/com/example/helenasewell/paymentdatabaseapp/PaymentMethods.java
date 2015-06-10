package com.example.helenasewell.paymentdatabaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Helena Sewell on 23/07/2014.
 * Methods relating to payments
 */
public class PaymentMethods {
    private SQLiteDatabase database;
    private PaymentHistoryHelper dbHelper;
    private String[] allColumns={ PaymentHistoryHelper.COLUMN_ID,PaymentHistoryHelper.COLUMN_DATE,
                                PaymentHistoryHelper.COLUMN_GROSS,PaymentHistoryHelper.COLUMN_NET};

    public PaymentMethods(Context context){
        dbHelper=new PaymentHistoryHelper(context);
    }

    public void open() throws SQLException {
        database=dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //creating a test payment object

    public Payment createPayment(){
        String dateIn=randomDate();
        int grossIn= 120+(int) Math.round(Math.random() * (500 - 120));
        int netIn= grossIn-100;

        //creates an entry to add values to
        ContentValues values=new ContentValues();
        values.put(PaymentHistoryHelper.COLUMN_DATE,dateIn);
        values.put(PaymentHistoryHelper.COLUMN_GROSS,grossIn);
        values.put(PaymentHistoryHelper.COLUMN_NET,netIn);

        //placing it in the database
        long insertId=database.insert(PaymentHistoryHelper.TABLE_PAYMENT,null,values);

        //returns the payment so the activity can add it to the PaymentAdapter
        Payment p=new Payment();
        p.setId(insertId);
        p.setDate(dateIn);
        p.setGross(grossIn);
        p.setNet(netIn);
        return p;
    }

    //creating a random date

    public String randomDate(){
        GregorianCalendar c=new GregorianCalendar();
        int year= 2000+(int) Math.round(Math.random() * (2020 - 2000));
        c.set(GregorianCalendar.YEAR,year);
        int day=1+(int) Math.round(Math.random() * c.getActualMaximum(GregorianCalendar.DAY_OF_YEAR)-1);
        c.set(GregorianCalendar.DAY_OF_YEAR,day);
        Date d=c.getTime();
        DateFormat df= DateFormat.getDateInstance();
        return df.format(d);
    }

    //retrieves a single Payment from the database

    public Payment getPayment(int id){
        String[] projection= new String[]{
                PaymentHistoryHelper.COLUMN_ID,PaymentHistoryHelper.COLUMN_DATE,
                PaymentHistoryHelper.COLUMN_GROSS,PaymentHistoryHelper.COLUMN_NET};
        Cursor c=database.query(PaymentHistoryHelper.TABLE_PAYMENT,projection,
                PaymentHistoryHelper.COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        if(c!=null)c.moveToFirst();
        Payment p=new Payment();
        p.setId(c.getLong(0));
        p.setDate(c.getString(1));
        p.setGross(c.getInt(2));
        p.setNet(c.getInt(3));
        c.close();
        return p;
    }

    //deletes a Payment

    public void deletePayment(Payment p){
        database.delete(PaymentHistoryHelper.TABLE_PAYMENT,PaymentHistoryHelper.COLUMN_ID
                +" = ?",new String[]{String.valueOf(p.getID())});
    }

    //retrieves all Payments in List<> form

    public List<Payment> getAllPayments(){
        List<Payment> paymentList=new ArrayList<Payment>();
        String[] projection= new String[]{
                PaymentHistoryHelper.COLUMN_ID,PaymentHistoryHelper.COLUMN_DATE,
                PaymentHistoryHelper.COLUMN_GROSS,PaymentHistoryHelper.COLUMN_NET};
        Cursor c=database.query(PaymentHistoryHelper.TABLE_PAYMENT,projection,
                PaymentHistoryHelper.COLUMN_ID,null,null,null,null);

        c.moveToFirst();
        while (!c.isAfterLast()){
                Payment p=new Payment();
                p.setId(c.getLong(0));
                p.setDate(c.getString(1));
                p.setGross(c.getInt(2));
                p.setNet(c.getInt(3));
                paymentList.add(p);
                c.moveToNext();
        }
        c.close();
        return paymentList;
    }


}
