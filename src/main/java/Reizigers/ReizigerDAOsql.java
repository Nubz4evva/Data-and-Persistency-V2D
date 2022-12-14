package Reizigers;

import OVChipkaart.OVChipkaart;
import Reizigers.Reiziger;
import Reizigers.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Adres.Adres;
import Adres.AdresDAOsql;

import OVChipkaart.OVChipkaartDAOsql;

public class ReizigerDAOsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Reiziger reiziger) {
        try {
            Statement st = conn.createStatement();
            String saveString = "INSERT INTO reiziger VALUES(?, ?, ?, ?, ?)";
            PreparedStatement saveReiziger = conn.prepareStatement(saveString);
            saveReiziger.setInt(1, reiziger.getId());
            saveReiziger.setString(2, reiziger.getVoorletters());
            saveReiziger.setString(3, reiziger.getTussenvoegsels());
            saveReiziger.setString(4, reiziger.getAchternaam());
            saveReiziger.setDate(5, reiziger.getGeboortedatum());
            saveReiziger.executeUpdate();
            st.close();
            // Save adres
            AdresDAOsql adresDAO = new AdresDAOsql(conn);
            adresDAO.save(reiziger.getAdres());
            // Save OVChipkaarten
            OVChipkaartDAOsql ovDAO = new OVChipkaartDAOsql(conn);
            for (OVChipkaart ovChipkaart : reiziger.getOVChipkaarten()) {
                ovDAO.save(ovChipkaart);
            }
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean update(Reiziger reiziger) {
        try {
            Statement st = conn.createStatement();
            String saveString = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement saveReiziger = conn.prepareStatement(saveString);
            saveReiziger.setString(1, reiziger.getVoorletters());
            saveReiziger.setString(2, reiziger.getTussenvoegsels());
            saveReiziger.setString(3, reiziger.getAchternaam());
            saveReiziger.setDate(4, reiziger.getGeboortedatum());
            saveReiziger.setInt(5, reiziger.getId());
            saveReiziger.executeUpdate();
            st.close();
            // Update adres
            AdresDAOsql adresDAO = new AdresDAOsql(conn);
            adresDAO.update(reiziger.getAdres());
            // Update OVChipkaarten
            OVChipkaartDAOsql ovDAO = new OVChipkaartDAOsql(conn);
            for (OVChipkaart ovChipkaart : reiziger.getOVChipkaarten()) {
                ovDAO.update(ovChipkaart);
            }
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(Reiziger reiziger) {
        try {
            // First delete adres due to FK constraint
            AdresDAOsql adresDAO = new AdresDAOsql(conn);
            adresDAO.delete(reiziger.getAdres());
            // Secondly delete OVChipkaarten due to FK constraint
            OVChipkaartDAOsql ovDAO = new OVChipkaartDAOsql(conn);
            for (OVChipkaart ovChipkaart : reiziger.getOVChipkaarten()) {
                ovDAO.delete(ovChipkaart);
            }
            // Delete reiziger last
            Statement st = conn.createStatement();
            String saveString = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement saveReiziger = conn.prepareStatement(saveString);
            saveReiziger.setInt(1, reiziger.getId());
            saveReiziger.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public Reiziger findById(int id) {
        try {
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement saveReiziger = conn.prepareStatement(saveString);
            saveReiziger.setInt(1, id);
            ResultSet rs = saveReiziger.executeQuery();
            st.close();

            rs.next();
            String vl = rs.getString("voorletters");
            String tv = rs.getString("tussenvoegsel");
            String an = rs.getString("achternaam");
            Date gbdtm = rs.getDate("geboortedatum");
            Reiziger reiziger = new Reiziger(id, vl, tv, an, gbdtm);
            // Load adres
            AdresDAOsql adresDAO = new AdresDAOsql(conn);
            Adres adres = adresDAO.findByReiziger(reiziger);
            reiziger.setAdres(adres);
            // Load OVChipkaarten
            OVChipkaartDAOsql ovDAO = new OVChipkaartDAOsql(conn);
            ArrayList<OVChipkaart> OVChipkaarten = ovDAO.findByReiziger(reiziger);
            reiziger.setOVChipkaarten(OVChipkaarten);
            return reiziger;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Reiziger> findByGbdatum(String datum) {
        try {
            Date gbdtm = java.sql.Date.valueOf(datum);
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement saveReiziger = conn.prepareStatement(saveString);
            saveReiziger.setDate(1, gbdtm);
            ResultSet rs = saveReiziger.executeQuery();
            st.close();
            AdresDAOsql adresDAO = new AdresDAOsql(conn);

            List<Reiziger> reizigerList = new ArrayList<Reiziger>();
            while (rs.next()) {
                String vl = rs.getString("voorletters");
                String tv = rs.getString("tussenvoegsel");
                String an = rs.getString("achternaam");
                int id = rs.getInt("reiziger_id");
                Reiziger reiziger = new Reiziger(id, vl, tv, an, gbdtm);
                // Load Adres
                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);
                // Load OVChipkaarten
                OVChipkaartDAOsql ovDAO = new OVChipkaartDAOsql(conn);
                ArrayList<OVChipkaart> OVChipkaarten = ovDAO.findByReiziger(reiziger);
                reiziger.setOVChipkaarten(OVChipkaarten);
                // Add to list
                reizigerList.add(reiziger);
            }
            return reizigerList;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Reiziger> findAll() {
        try {
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM reiziger";
            PreparedStatement saveReiziger = conn.prepareStatement(saveString);
            ResultSet rs = saveReiziger.executeQuery();
            st.close();
            AdresDAOsql adresDAO = new AdresDAOsql(conn);

            List<Reiziger> reizigerList = new ArrayList<Reiziger>();
            while (rs.next()) {
                int id = rs.getInt("reiziger_id");
                String vl = rs.getString("voorletters");
                String tv = rs.getString("tussenvoegsel");
                String an = rs.getString("achternaam");
                Date gbdtm = rs.getDate("geboortedatum");
                Reiziger reiziger = new Reiziger(id, vl, tv, an, gbdtm);
                // Load adres
                Adres adres = adresDAO.findByReiziger(reiziger);
                reiziger.setAdres(adres);
                // Load OVChipkaarten
                OVChipkaartDAOsql ovDAO = new OVChipkaartDAOsql(conn);
                ArrayList<OVChipkaart> OVChipkaarten = ovDAO.findByReiziger(reiziger);
                reiziger.setOVChipkaarten(OVChipkaarten);
                // Add to list
                reizigerList.add(reiziger);
            }
            return reizigerList;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
