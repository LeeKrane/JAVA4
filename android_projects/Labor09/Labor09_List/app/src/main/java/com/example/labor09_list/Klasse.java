package com.example.labor09_list;

import java.util.List;

public class Klasse {
    private String name;
    private List<Schueler> schuelerList;

    public Klasse (String name, List<Schueler> schuelerList) {
        this.name = name;
        this.schuelerList = schuelerList;
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
}
