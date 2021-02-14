package com.example.labor09_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int CSV_RESOURCE = R.raw.schueler_15_16;

    private ArrayAdapter<Klasse> klassenAdapter;
    private List<Klasse> klassen;
    private ListView lv_klassen;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        klassen = Klasse.fromCSV(Klasse.linesFromCSV(getResources().openRawResource(CSV_RESOURCE)));
        klassenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, klassen);
        lv_klassen = findViewById(R.id.lv_klassen);
        lv_klassen.setAdapter(klassenAdapter);
        lv_klassen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                // TODO: Zweite Activity
            }
        });
    }
}