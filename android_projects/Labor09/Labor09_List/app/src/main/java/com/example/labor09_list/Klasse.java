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

    static List<Klasse> fromCSV (List<String> lines) {
        List<Klasse> klassen = new ArrayList<>();
        List<String> klassenToRead = lines.stream()
                                            .map(s -> s.split(";")[1])
                                            .distinct()
                                            .collect(Collectors.toList());

        for (String klasse : klassenToRead) {
            klassen.add(new Klasse(klasse, lines.stream()
                                                .map(line -> line.split(";"))
                                                .filter(split -> split[1].equals(klasse))
                                                .map(split -> new Schueler(Integer.parseInt(split[0]), split[1], split[2], split[3]))
                                                .collect(Collectors.toList())));
        }

        return klassen;
    }

    static List<String> linesFromCSV (InputStream inputStream) {
        List<String> lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.ready())
                lines.add(reader.readLine());
        } catch (IOException e) {
            throw new IllegalArgumentException("Illegal resource!");
        }

        return lines;
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
