package com.example.labor09_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static final String SCHUELER_EXTRA = "com.example.labor09_list.SCHUELER";
    private final static int CSV_RESOURCE = R.raw.schueler_15_16;

    private ArrayAdapter<Klasse> klassenAdapter;
    private List<Klasse> klassen;
    private ListView lv_klassen;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        klassen = Klasse.fromCSV(getResources().openRawResource(CSV_RESOURCE));
        klassen.sort(Comparator.comparing(Klasse::getName));
        klassenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, klassen);
        lv_klassen = findViewById(R.id.lv_klassen);
        lv_klassen.setAdapter(klassenAdapter);
        lv_klassen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                openDetails(view, (Klasse) parent.getAdapter().getItem(position));
            }
        });
    }

    private void openDetails(View view, Klasse klasse) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putStringArrayListExtra(SCHUELER_EXTRA, klasse.getSchuelerList().stream()
                                                                .sorted(Comparator.comparing(Schueler::getKatNr))
                                                                .map(s -> s.toString())
                                                                .collect(Collectors.toCollection(ArrayList::new)));
        startActivity(intent);
    }
}