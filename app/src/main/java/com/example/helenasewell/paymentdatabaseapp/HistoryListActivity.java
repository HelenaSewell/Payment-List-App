package com.example.helenasewell.paymentdatabaseapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

//This activity creates and manages a database of Payments. It also displays them in a listview.
public class HistoryListActivity extends Activity {

    ArrayAdapter mAdapter;
    private PaymentMethods data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        data=new PaymentMethods(this);
        try {
            data.open();
        }catch (Exception e){
            Log.e("historylist oncreate", e.toString());
        }

        //taking data from the database and making a list from it
        List<Payment> vals=data.getAllPayments();

        //create an adapter for the list of Payments, using the "listitem" layout
        mAdapter=new PaymentAdapter(this,R.layout.listitem,vals);
        //creating a Listview to populate with "listitems"
        ListView v=(ListView)findViewById(android.R.id.list);
        v.setDivider(getResources().getDrawable(R.drawable.list_divider));
        v.setDividerHeight(1);
        v.setAdapter(mAdapter);
    }

    //custom ArrayAdapter specially for the List<> of Payments: takes the Payments and makes views for each of them

    private class PaymentAdapter extends ArrayAdapter<Payment> {
        public PaymentAdapter(Context context,int v,List<Payment> values){
            super(context,v,values);
        }

        @Override
        public View getView(int pos,View convertView,ViewGroup parent){
            if(convertView==null){
                LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView=inflater.inflate(R.layout.listitem,null);
            }

            TextView date=(TextView)convertView.findViewById(R.id.date);
            TextView gross=(TextView)convertView.findViewById(R.id.gross);
            TextView net=(TextView)convertView.findViewById(R.id.net);

            //retrieves the Payment and its data
            Payment p=getItem(pos);
            String stringGross=moneyAdapter(p.getGross());
            String stringNet=moneyAdapter(p.getNet());

            Log.d("getview", date.toString());

            //applies the data from that Payment to the view
            date.setText("Date: "+p.getDate());
            gross.setText("Gross: "+stringGross);
            net.setText("Net: "+stringNet);
            return convertView;
        }

        //for displaying money properly
        String moneyAdapter(int value){
            NumberFormat nf= NumberFormat.getCurrencyInstance();
            return nf.format(value);
        }
    }

    public void onClick(View v){
        Payment p=null;
        switch (v.getId()){
            case R.id.add:
                p=data.createPayment();
                mAdapter.add(p);
                break;
            case R.id.delete:
                if(mAdapter.getCount()>0){
                    p=(Payment)mAdapter.getItem(0);
                    data.deletePayment(p);
                    mAdapter.remove(p);
                }
            break;
        }
        //remember to tell the PaymentAdapter that data has been altered, updating the views
        mAdapter.notifyDataSetChanged();
    }


    //important to open and close access to the database when the app is paused
    @Override
    protected void onResume() {
        try{data.open();}catch (Exception e){
            Log.e("historylist onresume", e.toString());}
        super.onResume();
    }

    @Override
    protected void onPause() {
        data.close();
        super.onPause();
    }

}
