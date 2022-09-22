import Reizigers.Reiziger;
import Reizigers.ReizigerDAO;
import Reizigers.ReizigerDAOsql;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        ReizigerDAOsql rdao = new ReizigerDAOsql(conn);
        testReizigerDAO(rdao);
    }

    /**
     * P2. Reizigers.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reizigers.Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test Reizigers.ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] Reizigers.ReizigerDAO.findAll() geeft de volgende reizigers:\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na Reizigers.ReizigerDAO.save():\n");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        System.out.println();

        // Vind Reizigers op basis van geboortedatum
        System.out.println("[Test] findByGbdatum geeft de volgende reizigers:\n");
        List<Reiziger> reizigers1 = rdao.findByGbdatum(gbdatum);
        for (Reiziger r : reizigers1) {
            System.out.println(r +"\n");
        }
        System.out.println();

        // Vind Sietske op basis van id
        System.out.println("[Test] findById geeft de volgende reiziger:\n");
        Reiziger reizigerById = rdao.findById(77);
        System.out.println(reizigerById + "\n");
        System.out.println();

        // Verander achternaam Sietske
        sietske.setAchternaam("Willems");
        System.out.println("[Test] Update geeft de volgende output:\n");
        rdao.update(sietske);
        System.out.println(rdao.findById(77) + "\n");
        System.out.println();

        // Delete Sietske
        System.out.println("[Test] Eerst " + reizigers.size() + " reizigers, na Delete :\n");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }
}