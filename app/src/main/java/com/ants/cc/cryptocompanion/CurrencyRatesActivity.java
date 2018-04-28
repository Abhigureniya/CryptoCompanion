package com.ants.cc.cryptocompanion;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CurrencyRatesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rates);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String commodity = extras.getString("Commodity");
            String currency = extras.getString("Currency");
            Toast.makeText(this, commodity+" " + currency, Toast.LENGTH_SHORT).show();
            TextView t = (TextView)findViewById(R.id.textView);
            t.setText("Rate Card");
        }
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);

    }
}
