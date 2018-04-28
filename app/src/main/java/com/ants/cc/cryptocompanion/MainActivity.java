package com.ants.cc.cryptocompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] currencies = {"Indian", "Korean"};
    Integer[] resources = {R.drawable.inr,R.drawable.won};
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView)findViewById(R.id.listView3);
        lv.setAdapter(new CustomAdapter(this, currencies, resources));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, currencies[i], Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), CryptoActivity.class);
                intent.putExtra("Currency",currencies[i]);
                startActivity(intent);
            }
        });
    }
}
