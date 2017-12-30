package com.jelena.ishrana.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


public class Recept {
    private Long recept_id;
    private String naziv;
    private int vremePripreme;
    private int vremeKuvanja;

    // TODO
// private String uputstvo;
    private byte[] slika;

    //private MultipartFile slika; //A representation of an uploaded file received in a multipart request


    private List<Namirnica> listaNamirnica;
    private List<Integer> listaKolicina;

    public Recept() {
        // da mi ove liste ne budu null, da ih imam, da budu prazne tj. velicine 0
        listaNamirnica = new ArrayList<>();
        listaKolicina = new ArrayList<>();
        slika = null;
    }

    public Long getRecept_id() {
        return recept_id;
    }
    public void setRecept_id(Long recept_id) {
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

    public byte[] getSlika() {
        return slika;
    }

    public void setSlika(byte[] slika) {
        this.slika = slika;
    }

    /*
    public MultipartFile getSlika() {
        return slika;
    }

    public void setSlika(MultipartFile slika) {
        this.slika = slika;
    }
    */

    public List<Namirnica> getListaNamirnica() {
        return listaNamirnica;
    }

    public void setListaNamirnica(List<Namirnica> listaNamirnica) {
        this.listaNamirnica = listaNamirnica;
    }

    public List<Integer> getListaKolicina() {
        return listaKolicina;
    }

    public void setListaKolicina(List<Integer> listaKolicina) {
        this.listaKolicina = listaKolicina;
    }

    @Override
    public String toString() {
        return "Recept{" +
                "recept_id=" + recept_id +
                ", naziv='" + naziv + '\'' +
                ", vremePripreme=" + vremePripreme +
                ", vremeKuvanja=" + vremeKuvanja +
                ", listaNamirnica=" + listaNamirnica +
                ", listaKolicina=" + listaKolicina +
                '}';
    }
}
