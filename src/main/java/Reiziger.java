import java.time.LocalDate;
import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsels;
    private String achternaam;
    private Date geboortedatum;

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
        return "#" + this.id + " " + getNaam() + " (" + this.geboortedatum + ")";
    }
}
