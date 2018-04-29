package com.ants.cc.cryptocompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CryptoActivity extends AppCompatActivity {

    String[] currencies = {"Bitcoin", "Ethereum"};
    Integer[] resources = {R.drawable.bit,R.drawable.eth};
    Intent intent;
    String currency;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if(extras != null){
            currency = extras.getString("Currency");
            Toast.makeText(this, currency, Toast.LENGTH_SHORT).show();
            TextView t = (TextView)findViewById(R.id.textView);
            t.setText("CURRENCY");
            sp.edit().putString("Currency",currency).commit();
        }else{
            currency = sp.getString("Currency","Indian");
        }
        ListView lv = (ListView)findViewById(R.id.listView3);
        lv.setAdapter(new CustomAdapter(this, currencies, resources));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, currencies[i], Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), CurrencyRatesActivity.class);
                intent.putExtra("Commodity",currencies[i]);
                intent.putExtra("Currency",currency);
                startActivity(intent);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        },700);
    }
}
