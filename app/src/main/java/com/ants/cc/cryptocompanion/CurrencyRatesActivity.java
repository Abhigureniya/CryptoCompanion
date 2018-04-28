package com.ants.cc.cryptocompanion;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class CurrencyRatesActivity extends AppCompatActivity {

    String[] bitcoinAPIs = {
            "https://www.zebapi.com/api/v1/market/ticker-new/btc/inr",
            "https://coindelta.com/api/v1/public/getticker/",
            "https://koinex.in/api/ticker",
            "https://www.buyucoin.com/api/v1.2/currency/btc"

    };
    String[] ethereumAPIs = {
            "https://www.zebapi.com/api/v1/market/ticker-new/eth/inr",
            "https://coindelta.com/api/v1/public/getticker/",
            "https://koinex.in/api/ticker",
            "https://www.buyucoin.com/api/v1.2/currency/eth"

    };

    String commodity;

    TableLayout backupLayout;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rates);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            commodity = extras.getString("Commodity");
            String currency = extras.getString("Currency");
            Toast.makeText(this, commodity+" " + currency, Toast.LENGTH_SHORT).show();
            TextView t = (TextView)findViewById(R.id.textView);
            t.setText("Rate Card");
        }
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        backupLayout = tableLayout;
        /*ContentDownloader cd = new ContentDownloader();
        ContentDownloader cd1 = new ContentDownloader();
        cd.execute(bitcoinAPIs[0],bitcoinAPIs[1],bitcoinAPIs[2],bitcoinAPIs[3]);
        cd1.execute(ethereumAPIs[0],ethereumAPIs[1],ethereumAPIs[2],ethereumAPIs[3]);*/
        for(int i=1; i<=10; i++){
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView t1 = new TextView(this);
            TextView t2 = new TextView(this);
            TextView t3 = new TextView(this);
            t1.setText("Test Data");
            t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t2.setText("Test Data");
            t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t3.setText("Test Data");
            t3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            t2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            t3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr1.addView(t1);
            tr1.addView(t2);
            tr1.addView(t3);
            tableLayout.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }


    class ContentDownloader extends AsyncTask<String, Void, TableRow[]>{

        TableRow[] results;

        @Override
        protected TableRow[] doInBackground(String... urls) {
            ArrayList<String> al = new ArrayList<>();
            for(String s : urls){
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

            return results;
        }

        @Override
        protected void onPostExecute(TableRow[] tableRows) {
            /*for(TableRow t : tableRows){
                System.out.println(t);
            }*/
            System.out.println("Execution Complete");
        }
    }

}
