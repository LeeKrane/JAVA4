package com.example.labor09_list;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Klasse {
    private String name;
    private List<Schueler> schuelerList;

    public Klasse (String name) {
        this(name, new ArrayList<>());
    }

    public Klasse (String name, List<Schueler> schuelerList) {
        this.name = name;
        this.schuelerList = schuelerList;
    }

    static List<Klasse> fromCSV (InputStream inputStream) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(s -> Schueler.fromCSV(s))
                    .collect(Collectors.groupingBy(Schueler::getKlasse, Collectors.toList()))
                        .entrySet().stream()
                        .map(e -> new Klasse(e.getKey(), e.getValue()))
                        .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Illegal resource!");
        }
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<Schueler> getSchuelerList () {
        return schuelerList;
    }

    public void setSchuelerList (List<Schueler> schuelerList) {
        this.schuelerList = schuelerList;
    }

    public void addSchueler (Schueler schueler) {
        this.schuelerList.add(schueler);
    }

    public void removeSchueler (Schueler schueler) {
        this.schuelerList.remove(schueler);
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Klasse klasse = (Klasse) o;
        return Objects.equals(name, klasse.name);
    }

    @Override
    public int hashCode () {
        return Objects.hash(name);
    }

    @Override
    public String toString () {
        return name + " (" + schuelerList.size() + ")";
    }
}
