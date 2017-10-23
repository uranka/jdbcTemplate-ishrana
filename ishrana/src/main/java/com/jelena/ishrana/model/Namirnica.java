
package com.jelena.ishrana.model;

public class Namirnica {

    private Long namirnica_id;
    private String naziv;
    private double kcal;
    private double p;
    private double m;
    private double uh;
    private String kategorija;


    public Long getNamirnica_id() {
        return namirnica_id;
    }
    public void setNamirnica_id(long namirnica_id) {
        this.namirnica_id = namirnica_id;
    }

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public double getKcal() {
        return kcal;
    }
    public void setKcal(double kcal) {
        this.kcal = kcal;
    }
    public double getP() {
        return p;
    }
    public void setP(double p) {
        this.p = p;
    }
    public double getM() {
        return m;
    }
    public void setM(double m) {
        this.m = m;
    }
    public double getUh() {
        return uh;
    }
    public void setUh(double uh) {
        this.uh = uh;
    }
    public String getKategorija() {
        return kategorija;
    }
    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }
    @Override
    public String toString() {
        return "Namirnica [namirnica_id=" + namirnica_id + ", naziv=" + naziv + ", kcal=" + kcal + ", p=" + p + ", m="
                + m + ", uh=" + uh + ", kategorija=" + kategorija + "]";
    }


}
