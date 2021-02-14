package com.example.labor09_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int CSV_RESOURCE = R.raw.schueler_15_16;
    private List<Schueler> schuelerList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schuelerList = readSchuelerFromCSV(CSV_RESOURCE);
    }

    private List<Schueler> readSchuelerFromCSV (int resource) {
        List<Schueler> schuelerList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(resource)))) {
            while (reader.ready())
                schuelerList.add(Schueler.fromCSV(reader.readLine()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Illegal resource!");
        }

        return schuelerList;
    }
}