package com.example.labor09_list;

public class Schueler {
    private int katNr;
    private String klasse;
    private String vorname;
    private String nachname;

    public Schueler (int katNr, String klasse, String vorname, String nachname) {
        this.katNr = katNr;
        this.klasse = klasse;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public static Schueler fromCSV (String line) {
        String[] split = line.split(";");
        if (split.length != 4)
            throw new IllegalArgumentException("Corrupted CSV file!");
        return new Schueler(Integer.parseInt(split[0]), split[1], split[2], split[3]);
    }

    public int getKatNr () {
        return katNr;
    }

    public void setKatNr (int katNr) {
        this.katNr = katNr;
    }

    public String getKlasse () {
        return klasse;
    }

    public void setKlasse (String klasse) {
        this.klasse = klasse;
    }

    public String getVorname () {
        return vorname;
    }

    public void setVorname (String vorname) {
        this.vorname = vorname;
    }

    public String getNachname () {
        return nachname;
    }

    public void setNachname (String nachname) {
        this.nachname = nachname;
    }
}
