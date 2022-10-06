package OVChipkaart;

import Product.Product;
import Reizigers.Reiziger;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

public class OVChipkaart {
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;
    private ArrayList<Product> producten = new ArrayList<Product>();

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
    }

    public void addProduct(Product product) {
        product.addOVChipkaart(this);
        this.producten.add(product);
    }

    public void removeProduct(Product product) {
        this.producten.remove(product);
        ArrayList<OVChipkaart> pOVChipkaarten = product.getOVChipkaarten();
        pOVChipkaarten.remove(this);
    }

    public String toString() {
        return "Kaartnummer " + this.kaartNummer + " (Klasse " + this.klasse + "), saldo: " + this.saldo;
    }
}
