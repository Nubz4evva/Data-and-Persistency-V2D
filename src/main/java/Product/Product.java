package Product;

import OVChipkaart.OVChipkaart;

import java.util.ArrayList;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private ArrayList<OVChipkaart> OVChipkaarten = new ArrayList<OVChipkaart>();

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setOVChipkaarten(ArrayList<OVChipkaart> OVChipkaarten) {
        this.OVChipkaarten = OVChipkaarten;
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        OVChipkaarten.add(ovChipkaart);
    }

    public void removeOVChipkaart(OVChipkaart ovChipkaart) {
        this.OVChipkaarten.remove(ovChipkaart);
        ArrayList<Product> OVProducten = ovChipkaart.getProducten();
        OVProducten.remove(this);
    }

    public String toString() {
        return naam + " (€\u200E" + this.prijs + ") : " + beschrijving;
    }
}
