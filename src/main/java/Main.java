import Reizigers.Reiziger;
import Reizigers.ReizigerDAO;
import Reizigers.ReizigerDAOsql;

import java.sql.*;
import java.util.List;

import Adres.Adres;
import Adres.AdresDAO;
import Adres.AdresDAOsql;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        ReizigerDAOsql rdao = new ReizigerDAOsql(conn);
        AdresDAOsql adao = new AdresDAOsql(conn);
        testP3(rdao, adao);
    }


    private static void testP3(ReizigerDAO rdao, AdresDAOsql adao) throws SQLException {
        System.out.println("\n---------- Test P3 -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] Reizigers.ReizigerDAO.findAll() geeft de volgende reizigers en adressen:\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger en adres aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres adres = new Adres(106, "3853SL", "24", "Leemkuul", "Ermelo", 77);
        sietske.setAdres(adres);
        rdao.save(sietske);
        reizigers = rdao.findAll();

        // Vind Sietske op basis van id
        System.out.println("[Test] Save + findById geeft de volgende reiziger en adres:\n");
        Reiziger reizigerById = rdao.findById(77);
        System.out.println(reizigerById + "\n");
        System.out.println();

        // Vind Reizigers op basis van geboortedatum
        System.out.println("[Test] findByGbdatum geeft de volgende reizigers en adressen:\n");
        List<Reiziger> reizigers1 = rdao.findByGbdatum(gbdatum);
        for (Reiziger r : reizigers1) {
            System.out.println(r +"\n");
        }
        System.out.println();

        // Verander huisnummer Sietske
        sietske.getAdres().setHuisnummer("26");
        System.out.println("[Test] Update geeft de volgende output:\n");
        rdao.update(sietske);
        System.out.println(rdao.findById(77) + "\n");
        System.out.println();

        // Delete Sietske
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] Eerst " + reizigers.size() + " reizigers en " + adressen.size() + " adressen, na Delete :\n");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        adressen = adao.findAll();
        System.out.println(reizigers.size() + " reizigers en " + adressen.size() + " adressen \n");
    }
}