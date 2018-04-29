package com.ants.cc.cryptocompanion;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CurrencyRatesActivity extends AppCompatActivity {

    String[] bitcoinAPIs = {
            "https://www.zebapi.com/api/v1/market/ticker-new/btc/inr",
            "https://coindelta.com/api/v1/public/getticker/",
            "https://koinex.in/api/ticker",
            "https://www.buyucoin.com/api/v1.2/currency/btc"

    };
    String[] bitcoinAPISNames = {"ZebPay","CoinDelta","Koinex","BuyUCoin"};
    String[] ethereumAPIs = {
            "https://www.zebapi.com/api/v1/market/ticker-new/eth/inr",
            "https://coindelta.com/api/v1/public/getticker/",
            "https://koinex.in/api/ticker",
            "https://www.buyucoin.com/api/v1.2/currency/eth"

    };

    ProgressBar progressBar2;

    String commodity;

    TableLayout tableLayout;

    Handler handler = new Handler();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rates);
        Bundle extras = getIntent().getExtras();
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.VISIBLE);
        if(extras != null){
            commodity = extras.getString("Commodity");
            String currency = extras.getString("Currency");
            Toast.makeText(this, commodity+" " + currency, Toast.LENGTH_SHORT).show();
            TextView t = (TextView)findViewById(R.id.textView);
            t.setText("Rate Card");
        }
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        populateData();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                populateData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CurrencyRatesActivity.this, "Data Refrteshed", Toast.LENGTH_SHORT).show();
                        if(tableLayout.getChildCount()!=1){
                                    tableLayout.removeView(tableLayout.getChildAt(1));
                                    tableLayout.removeView(tableLayout.getChildAt(1));
                                    tableLayout.removeView(tableLayout.getChildAt(1));
                                    tableLayout.removeView(tableLayout.getChildAt(1));
                                    //Toast.makeText(CurrencyRatesActivity.this, "Removed Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                handler.postDelayed(this, 20000);
            }
        }, 20000);

        System.out.println("OnCreate Method Finished");


    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    public void populateData(){
        ContentDownloader cd = new ContentDownloader();
        if(commodity.equals("Bitcoin")){
            cd.execute(bitcoinAPIs[0],bitcoinAPIs[1],bitcoinAPIs[2],bitcoinAPIs[3]);
        }else if(commodity.equals("Ethereum")){
            cd.execute(ethereumAPIs[0],ethereumAPIs[1],ethereumAPIs[2],ethereumAPIs[3]);
        }
    }


    class ContentDownloader extends AsyncTask<String, Void, TableRow[]>{

        TableRow[] results = new TableRow[4];

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected TableRow[] doInBackground(String... urls) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar2.setVisibility(View.VISIBLE);
                }
            });
            ArrayList<String> al = new ArrayList<>();
            for(int i=0; i<=3; i++){
                String s = urls[i];
                try {
                    URL url = new URL(s);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String result = "";
                    int data = inputStream.read();
                    while(data != -1){
                        char c = (char)data;
                        result += c;
                        data = inputStream.read();
                    }
                    Log.i("Returned",result);
                    al.add(result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

                for(int i=0; i<=3; i++){
                    TableRow tr1 = new TableRow(getApplicationContext());
                    tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView t1 = new TextView(getApplicationContext());
                    TextView t2 = new TextView(getApplicationContext());
                    TextView t3 = new TextView(getApplicationContext());
                    t1.setText(bitcoinAPISNames[i]);
                    t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    if(i==0){
                        try {
                            JSONObject jo = new JSONObject(al.get(i));
                            t2.setText(jo.getString("buy"));
                            t3.setText(jo.getString("sell"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(i==1){
                        try {
                            JSONArray ja = new JSONArray(al.get(i));
                            JSONObject jo;
                            if(commodity.equals("Bitcoin")){
                                jo = ja.getJSONObject(0);
                            }else{
                                jo = ja.getJSONObject(1);
                            }
                            t2.setText(jo.getString("Ask"));
                            t3.setText(jo.getString("Bid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(i==2){
                        try {
                            JSONObject jo = new JSONObject(al.get(i));
                            JSONObject stats = jo.getJSONObject("stats");
                            JSONObject inr = stats.getJSONObject("inr");
                            JSONObject btc;
                            if(commodity.equals("Bitcoin")){
                                btc = inr.getJSONObject("BTC");
                            }else{
                                btc = inr.getJSONObject("ETH");
                            }
                            t2.setText(btc.getString("lowest_ask"));
                            t3.setText(btc.getString("highest_bid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(i==3){
                        try {
                            JSONObject jo = new JSONObject(al.get(i));
                            JSONObject data = jo.getJSONObject("data");
                            t2.setText(data.getString("ask"));
                            t3.setText(data.getString("bid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    t3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    t2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    t3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr1.addView(t1);
                    tr1.addView(t2);
                    tr1.addView(t3);
                    results[i]=tr1;
                    //tableLayout.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }


            return results;
        }

        @Override
        protected void onPostExecute(TableRow[] tableRows) {
            for(TableRow t : tableRows){
                //System.out.println(t);
                tableLayout.addView(t, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
            System.out.println("Execution Complete");
            progressBar2.setVisibility(View.INVISIBLE);
        }
    }

}
