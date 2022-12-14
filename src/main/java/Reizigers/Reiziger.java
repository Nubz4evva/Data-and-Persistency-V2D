package Reizigers;

import java.time.LocalDate;

import Adres.Adres;
import OVChipkaart.OVChipkaart;

import java.sql.Date;
import java.util.ArrayList;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsels;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private ArrayList<OVChipkaart> OVChipkaarten;

    public Reiziger(int id, String vl, String tv, String an, Date gbdtm) {
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsels = tv;
        this.achternaam = an;
        this.geboortedatum = gbdtm;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return this.voorletters;
    }
    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsels() {
        return this.tussenvoegsels;
    }
    public void setTussenvoegsels(String tussenvoegsels) {
        this.tussenvoegsels = tussenvoegsels;
    }

    public String getAchternaam() {
        return this.achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public ArrayList<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setOVChipkaarten(ArrayList<OVChipkaart> OVChipkaarten) {
        this.OVChipkaarten = OVChipkaarten;
    }

    public String getNaam() {
        String voorl = this.voorletters;
        String tussenv = this.tussenvoegsels;
        String achtern = this.achternaam;
        String text = voorl + ".";
        if (tussenv != null) {
            text += tussenv + " ";
        }
        text += achtern;
        return text;
    }

    public String toString() {
        String reizigerString = "Reiziger #" + this.id + " " + getNaam() + ", geb." + this.geboortedatum;
        String adresString = ", " + this.adres.getStraat() + " " + this.adres.getHuisnummer() + " (" + this.adres.getPostcode() + ")";
        return reizigerString + adresString;
    }
}
