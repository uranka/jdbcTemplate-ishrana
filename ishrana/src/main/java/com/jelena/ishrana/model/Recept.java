package com.jelena.ishrana.model;

import java.util.Map;

public class Recept {
    private Long recept_id;
    private String naziv;
    private int vremePripreme;
    private int vremeKuvanja;

    // TODO
// private String uputstvo;
// slika recepta

//	Map<Namirnica, Integer> mapa koja povezuje namirnicu sa kolicinom,
// za ovo mi treba da uradim hashcode i equals klase Namirnica  klase
// Namirnica je jedinstvena ona je kljuc, a kolicina nije, ona je value
// recept se pravi od namirnica koje postoje u bazi

    private Map<Namirnica, Integer> mapaNamirnica;

    public long getRecept_id() {
        return recept_id;
    }
    public void setRecept_id(long recept_id) {
        this.recept_id = recept_id;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVremePripreme() {
        return vremePripreme;
    }

    public void setVremePripreme(int vremePripreme) {
        this.vremePripreme = vremePripreme;
    }

    public int getVremeKuvanja() {
        return vremeKuvanja;
    }

    public void setVremeKuvanja(int vremeKuvanja) {
        this.vremeKuvanja = vremeKuvanja;
    }

    public Map<Namirnica, Integer> getMapaNamirnica() {
        return mapaNamirnica;
    }
    public void setMapaNamirnica(Map<Namirnica, Integer> mapaNamirnica) {
        this.mapaNamirnica = mapaNamirnica;
    }

    @Override
    public String toString() {
        return "Recept [recept_id=" + recept_id + ", naziv=" + naziv + ", mapaNamirnica=" + mapaNamirnica + "]";
    }



}
